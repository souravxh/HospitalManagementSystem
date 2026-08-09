package dao;

import database.DBConnection;
import model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PatientDAO {


    // ==========================
    // ADD PATIENT
    // ==========================

    public boolean addPatient(Patient p){


        String sql =
                "INSERT INTO patients(name,age,gender,disease,phone,address,blood_group) VALUES(?,?,?,?,?,?,?)";


        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)

        ){


            ps.setString(1,p.getName());

            ps.setInt(2,p.getAge());

            ps.setString(3,p.getGender());

            ps.setString(4,p.getDisease());

            ps.setString(5,p.getPhone());

            ps.setString(6,p.getAddress());

            ps.setString(7,p.getBloodGroup());


            return ps.executeUpdate()>0;


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return false;

    }





    // ==========================
    // GET ALL PATIENTS
    // ==========================

    public List<Patient> getAllPatients(){


        List<Patient> list =
                new ArrayList<>();


        String sql =
                "SELECT * FROM patients ORDER BY id DESC";



        try(
                Connection con =
                        DBConnection.getConnection();

                Statement st =
                        con.createStatement();

                ResultSet rs =
                        st.executeQuery(sql)

        ){


            while(rs.next()){


                Patient p =
                        new Patient();


                p.setId(
                        rs.getInt("id")
                );


                p.setName(
                        rs.getString("name")
                );


                p.setAge(
                        rs.getInt("age")
                );


                p.setGender(
                        rs.getString("gender")
                );


                p.setDisease(
                        rs.getString("disease")
                );


                p.setPhone(
                        rs.getString("phone")
                );


                p.setAddress(
                        rs.getString("address")
                );


                p.setBloodGroup(
                        rs.getString("blood_group")
                );



                list.add(p);

            }


        }
        catch(Exception e){

            e.printStackTrace();

        }



        return list;

    }






    // ==========================
    // PATIENT LIST FOR APPOINTMENT DROPDOWN
    // ==========================

    public List<Patient> getPatientList(){


        List<Patient> list =
                new ArrayList<>();


        String sql =
                "SELECT id,name FROM patients";



        try(
                Connection con =
                        DBConnection.getConnection();

                Statement st =
                        con.createStatement();

                ResultSet rs =
                        st.executeQuery(sql)

        ){



            while(rs.next()){


                Patient p =
                        new Patient();


                p.setId(
                        rs.getInt("id")
                );


                p.setName(
                        rs.getString("name")
                );


                list.add(p);


            }


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return list;

    }







    // ==========================
    // UPDATE PATIENT
    // ==========================

    public boolean updatePatient(Patient p){


        String sql =
                """
                UPDATE patients SET
        
                name=?,
        
                age=?,
        
                gender=?,
        
                disease=?,
        
                phone=?,
        
                address=?,
        
                blood_group=?
        
                WHERE id=?
        
                """;



        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)

        ){


            ps.setString(1,p.getName());

            ps.setInt(2,p.getAge());

            ps.setString(3,p.getGender());

            ps.setString(4,p.getDisease());

            ps.setString(5,p.getPhone());

            ps.setString(6,p.getAddress());

            ps.setString(7,p.getBloodGroup());

            ps.setInt(8,p.getId());



            return ps.executeUpdate()>0;


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return false;

    }







    // ==========================
    // DELETE PATIENT
    // ==========================

    public boolean deletePatient(int id){


        String sql =
                "DELETE FROM patients WHERE id=?";



        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)

        ){


            ps.setInt(1,id);


            return ps.executeUpdate()>0;


        }
        catch(Exception e){

            e.printStackTrace();

        }


        return false;

    }







    // ==========================
    // SEARCH PATIENT
    // ==========================

    public List<Patient> searchPatient(String keyword){


        List<Patient> list =
                new ArrayList<>();


        String sql =
                """
                SELECT * FROM patients
        
                WHERE name LIKE ?
        
                OR phone LIKE ?
        
                OR disease LIKE ?
        
                """;



        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)

        ){


            String search =
                    "%" + keyword + "%";


            ps.setString(1,search);

            ps.setString(2,search);

            ps.setString(3,search);



            ResultSet rs =
                    ps.executeQuery();



            while(rs.next()){


                Patient p =
                        new Patient();


                p.setId(
                        rs.getInt("id")
                );


                p.setName(
                        rs.getString("name")
                );


                p.setAge(
                        rs.getInt("age")
                );


                p.setGender(
                        rs.getString("gender")
                );


                p.setDisease(
                        rs.getString("disease")
                );


                p.setPhone(
                        rs.getString("phone")
                );


                p.setAddress(
                        rs.getString("address")
                );


                p.setBloodGroup(
                        rs.getString("blood_group")
                );


                list.add(p);


            }



        }
        catch(Exception e){

            e.printStackTrace();

        }


        return list;

    }


}