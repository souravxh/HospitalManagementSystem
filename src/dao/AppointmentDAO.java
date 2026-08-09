package dao;

import database.DBConnection;
import model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AppointmentDAO {



    // ==========================
    // ADD APPOINTMENT
    // ==========================

    public boolean addAppointment(Appointment appointment){


        String sql =
                "INSERT INTO appointments(patient_id,doctor_id,appointment_date,appointment_time,status) VALUES(?,?,?,?,?)";


        try(

                Connection con =
                        DBConnection.getConnection();


                PreparedStatement ps =
                        con.prepareStatement(sql)

        ){


            ps.setInt(
                    1,
                    appointment.getPatientId()
            );


            ps.setInt(
                    2,
                    appointment.getDoctorId()
            );


            ps.setString(
                    3,
                    appointment.getAppointmentDate()
            );


            ps.setString(
                    4,
                    appointment.getAppointmentTime()
            );


            ps.setString(
                    5,
                    appointment.getStatus()
            );



            return ps.executeUpdate()>0;


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return false;

    }








    // ==========================
    // GET ALL APPOINTMENTS
    // ==========================

    public List<Appointment> getAllAppointments(){


        List<Appointment> list =
                new ArrayList<>();


        String sql =
                "SELECT * FROM appointments ORDER BY id DESC";



        try(

                Connection con =
                        DBConnection.getConnection();


                Statement st =
                        con.createStatement();


                ResultSet rs =
                        st.executeQuery(sql)

        ){



            while(rs.next()){


                Appointment a =
                        new Appointment();



                a.setId(
                        rs.getInt("id")
                );


                a.setPatientId(
                        rs.getInt("patient_id")
                );


                a.setDoctorId(
                        rs.getInt("doctor_id")
                );


                a.setAppointmentDate(
                        rs.getString("appointment_date")
                );


                a.setAppointmentTime(
                        rs.getString("appointment_time")
                );


                a.setStatus(
                        rs.getString("status")
                );



                list.add(a);

            }


        }
        catch(Exception e){

            e.printStackTrace();

        }



        return list;

    }








    // ==========================
    // UPDATE APPOINTMENT
    // ==========================

    public boolean updateAppointment(Appointment appointment){


        String sql =
                """
                UPDATE appointments SET
        
                patient_id=?,
        
                doctor_id=?,
        
                appointment_date=?,
        
                appointment_time=?,
        
                status=?
        
                WHERE id=?
        
                """;



        try(

                Connection con =
                        DBConnection.getConnection();


                PreparedStatement ps =
                        con.prepareStatement(sql)

        ){



            ps.setInt(
                    1,
                    appointment.getPatientId()
            );


            ps.setInt(
                    2,
                    appointment.getDoctorId()
            );


            ps.setString(
                    3,
                    appointment.getAppointmentDate()
            );


            ps.setString(
                    4,
                    appointment.getAppointmentTime()
            );


            ps.setString(
                    5,
                    appointment.getStatus()
            );


            ps.setInt(
                    6,
                    appointment.getId()
            );



            return ps.executeUpdate()>0;


        }
        catch(Exception e){

            e.printStackTrace();

        }



        return false;

    }








    // ==========================
    // DELETE APPOINTMENT
    // ==========================

    public boolean deleteAppointment(int id){



        String sql =
                "DELETE FROM appointments WHERE id=?";



        try(

                Connection con =
                        DBConnection.getConnection();


                PreparedStatement ps =
                        con.prepareStatement(sql)

        ){



            ps.setInt(
                    1,
                    id
            );


            return ps.executeUpdate()>0;


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return false;

    }









    // ==========================
    // SEARCH APPOINTMENT
    // ==========================

    public List<Appointment> searchAppointment(String keyword){


        List<Appointment> list =
                new ArrayList<>();


        String sql =
                """
                SELECT * FROM appointments
        
                WHERE status LIKE ?
        
                OR appointment_date LIKE ?
        
                """;



        try(

                Connection con =
                        DBConnection.getConnection();


                PreparedStatement ps =
                        con.prepareStatement(sql)

        ){



            String search =
                    "%" + keyword + "%";



            ps.setString(
                    1,
                    search
            );


            ps.setString(
                    2,
                    search
            );



            ResultSet rs =
                    ps.executeQuery();




            while(rs.next()){


                Appointment a =
                        new Appointment();



                a.setId(
                        rs.getInt("id")
                );


                a.setPatientId(
                        rs.getInt("patient_id")
                );


                a.setDoctorId(
                        rs.getInt("doctor_id")
                );


                a.setAppointmentDate(
                        rs.getString("appointment_date")
                );


                a.setAppointmentTime(
                        rs.getString("appointment_time")
                );


                a.setStatus(
                        rs.getString("status")
                );



                list.add(a);

            }


        }
        catch(Exception e){

            e.printStackTrace();

        }



        return list;

    }


}