package com.example.taruc.lab42database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

//TODO 6: Create a repository class to manage data query thread

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);

        userDao = db.userDao();
        allUsers = userDao.loadAllUsers();
    }
    
    LiveData<List<User>> getAllUsers(){
        return allUsers;
    }
    
    public void insertUser(User user){
        new insertAsyncTask(userDao).execute(user);
    }
    public void deleteAll(User user){

    }

    //<Param, Progress, Results>
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public insertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }

        private static class deleteUserAsyncTask extends AsyncTask<User, Void, Void> {
            private UserDao userAsyncTaskDao;

            deleteUserAsyncTask(UserDao dao) {
                userAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final User... params) {
                userAsyncTaskDao.deleteUser(params[0]);
                return null;
            }
        }


        public void deleteUser(User user)  {
            new deleteUserAsyncTask(userDao).execute(user);
        }
    }

}
