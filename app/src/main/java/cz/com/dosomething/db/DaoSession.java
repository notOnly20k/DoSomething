package cz.com.dosomething.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import cz.com.dosomething.bean.Task;
import cz.com.dosomething.bean.TaskInfo;

import cz.com.dosomething.db.TaskDao;
import cz.com.dosomething.db.TaskInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig taskDaoConfig;
    private final DaoConfig taskInfoDaoConfig;

    private final TaskDao taskDao;
    private final TaskInfoDao taskInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        taskDaoConfig = daoConfigMap.get(TaskDao.class).clone();
        taskDaoConfig.initIdentityScope(type);

        taskInfoDaoConfig = daoConfigMap.get(TaskInfoDao.class).clone();
        taskInfoDaoConfig.initIdentityScope(type);

        taskDao = new TaskDao(taskDaoConfig, this);
        taskInfoDao = new TaskInfoDao(taskInfoDaoConfig, this);

        registerDao(Task.class, taskDao);
        registerDao(TaskInfo.class, taskInfoDao);
    }
    
    public void clear() {
        taskDaoConfig.clearIdentityScope();
        taskInfoDaoConfig.clearIdentityScope();
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public TaskInfoDao getTaskInfoDao() {
        return taskInfoDao;
    }

}
