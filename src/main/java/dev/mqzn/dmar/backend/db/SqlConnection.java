package dev.mqzn.dmar.backend.db;

import dev.mqzn.dmar.util.Conversions;
import dev.mqzn.dmar.DmarPvP;
import dev.mqzn.dmar.core.UserData;
import dev.mqzn.dmar.util.Logger;
import dev.mqzn.dmar.util.Tasks;
import org.bukkit.configuration.file.FileConfiguration;
import java.sql.*;

public class SqlConnection {

    private Connection connection;
    private final String URL, USER, PASSWORD;

    public SqlConnection() {

        FileConfiguration conf = DmarPvP.getInstance().getConfig();

        String p = "MySQL.";
        String host = conf.getString(p + "host");
        int port = conf.getInt(p + "port");
        String db = conf.getString(p + "database");

        URL = "jdbc:mysql://" +  host  + ":" + port + "/" + db;
        USER = conf.getString(p + "user");
        PASSWORD = conf.getString(p + "password");
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() {

        if(!isConnected()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                this.prepareStatement("CREATE IF NOT EXISTS PlayersData(" +
                        "UUID BINARY(16), Kills INT, Deaths INT, HighestKS INT, " +
                        "Coins INT, Points INT, RodTrail VARCHAR(16), ArrowTrail VARCHAR(16), " +
                        "BattleCry VARCHAR(16), SelectedPerks VARCHAR(50), SpecialItems VARCHAR(50))");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public synchronized void insertData(UserData data) {

        Tasks.runAsync(()-> {
            try (PreparedStatement st =
                         this.prepareStatement(
                                 "INSERT INTO PlayersData(UUID, Kills, Deaths, HighestKS, Coins, Points, " +
                                         "RodTrail, ArrowTrail, BattleCry, SelectedPerks, SpecialItems) " +
                                         "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")){

                st.setBytes(1, Conversions.uuidToBytes(data.getId()));
                st.setInt(2, data.getKills());
                st.setInt(3, data.getDeaths());
                st.setInt(4, data.getHighestKillStreak());
                st.setInt(5, data.getCoins());
                st.setInt(6, data.getPoints());
                st.setString(7, data.getRodTrail() != null ? data.getRodTrail().getName() : "NULL");
                st.setString(8, data.getArrowsTrail() != null ? data.getArrowsTrail().getName() : "NULL");
                st.setString(9, data.getBattleCry() != null ? data.getBattleCry().getDisplayName() : "NULL");
                st.setString(10, data.getSelectedPerks().isEmpty() ? "NULL" : Conversions.serializePerksMap(data.getSelectedPerks()));
                st.setString(11, data.getSpecialItems().isEmpty() ? "NULL" : Conversions.serializeItemsMap(data.getSpecialItems()));

                st.execute();
            } catch (SQLException | NullPointerException e) {
                System.out.println("CAUSE: " + e.getMessage());
            }
        });


    }

    public synchronized void updateData(UserData data, DataUpdate update) {

        Tasks.runAsync(()->{

            try(PreparedStatement st = this.prepareStatement("UPDATE PlayersData SET "
                    + update.getColumn() + "=? WHERE UUID=?")) {

                st.setString(1, (String) update.getValue(data));
                st.setBytes(2, Conversions.uuidToBytes(data.getId()));
                st.executeUpdate();

            }catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

    }

    public synchronized void updateData(UserData data) {

        Tasks.runAsync(()->{

            StringBuilder builder = new StringBuilder();
            DataUpdate[] arr = DataUpdate.values();
            int arrLength = arr.length;
            for (int i = 0; i < arrLength; i++) {
                builder.append(arr[i].getColumn()).append("=?")
                        .append(i == arrLength - 1 ? "" : ", ");
            }

            String statement = "UPDATE PlayersData SET "
                    + builder.toString() + " WHERE UUID=?";


            try(PreparedStatement st = this.prepareStatement(statement)) {

                for (int i = 0; i < arrLength; i++) {
                    int index = i+1;
                    if(arr[i].getUpdateType() == DataUpdate.UpdateType.INT) {
                        st.setInt(index, Integer.parseInt(String.valueOf(arr[i].getValue(data))));
                    }
                    else if(arr[i].getUpdateType() == DataUpdate.UpdateType.STRING) {
                        st.setString(index, String.valueOf(arr[i].getValue(data)));
                    }else {
                        break;
                    }
                }
                st.setBytes(arrLength+1, Conversions.uuidToBytes(data.getId()));
                st.executeUpdate();

            }catch (SQLException ex) {
                ex.printStackTrace();
                Logger.log("&eEXECUTING THE UPDATE >>  &7" + statement, Logger.LogType.NOTIFY);
            }


        });

    }

    public PreparedStatement prepareStatement(String statement) throws SQLException {
        return connection.prepareStatement(statement);
    }


}
