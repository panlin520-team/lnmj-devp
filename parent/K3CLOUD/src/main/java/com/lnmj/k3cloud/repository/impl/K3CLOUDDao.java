package com.lnmj.k3cloud.repository.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.k3cloud.comfig.DBUtil;
import com.lnmj.k3cloud.entity.DataCentre;
import com.lnmj.k3cloud.entity.Organization;
import com.lnmj.k3cloud.pojo.Incident;
import com.lnmj.k3cloud.repository.IIncidentDao;
import com.lnmj.k3cloud.repository.IK3CLOUDDao;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/10/10 09:47
 * @Description: 事件接口实现
 */
@Repository
public class K3CLOUDDao implements IK3CLOUDDao {
    @Override
    public PageInfo<DataCentre> selectK3DataCentreList(int pageNum, int pageSize, String dataCentreName) {
        dataCentreName = "K3DBConfiger20191165913949"; //正式服务器上的
        // dataCentreId = "K3DBConfiger2020325919345"; //192.168.100.2 服务器上的

        PageHelper.startPage(pageNum, pageSize);
        DBUtil dbUtil = new DBUtil();
        Connection conn = dbUtil.getConnection(dataCentreName);
        //3.通过数据库的连接操作数据库，实现增删改查
        List<DataCentre> dataCentreList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select FDATACENTERID,FNUMBER,FDATABASENAME from T_BAS_DATACENTER where FRUNTASK = 1");
            while (rs.next()) {//如果对象中有数据，就会循环打印出来
                DataCentre dataCentre = new DataCentre();
                dataCentre.setFDATACENTERID(rs.getString("fDATACENTERID"));
                dataCentre.setFNUMBER(rs.getString("fNUMBER"));
                dataCentre.setFDATABASENAME(rs.getString("fDATABASENAME"));
                dataCentreList.add(dataCentre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PageInfo pageInfo = new PageInfo(dataCentreList);
        return pageInfo;

    }

    @Override
    public Organization selectK3LegalPersonNumber(String dataCentreName) {
        DBUtil dbUtil = new DBUtil();
        Connection conn = dbUtil.getConnection(dataCentreName);
        //3.通过数据库的连接操作数据库，实现增删改查
        Organization organization = new Organization();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select FNUMBER from T_ORG_ORGANIZATIONS where FACCTORGTYPE = 1");
            while (rs.next()) {//如果对象中有数据，就会循环打印出来
                organization.setFNUMBER(rs.getString("fNUMBER"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organization;
    }

    @Override
    public Boolean orgToUser(String dataCentreName, String userId, String orgId) {
        DBUtil dbUtil = new DBUtil();
        Connection conn = dbUtil.getConnection(dataCentreName);
        Boolean result = null;
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("insert into T_SEC_USERORG values ((select MAX(FENTITYID) FROM T_SEC_USERORG)+1," + userId + ',' + orgId + ")");
            ResultSet FENTITYID = stmt.executeQuery("select FENTITYID from T_SEC_USERORG where FUSERID=" + userId + " AND FORGID=" + orgId);
            String fENTITYID = null;
            while (FENTITYID.next()) {//如果对象中有数据，就会循环打印出来
                fENTITYID = FENTITYID.getString("fENTITYID");
            }
            ResultSet COUNTS = stmt.executeQuery("select count(1) as COUNTS from T_SEC_USERROLEMAP");
            Integer count = null;
            while (COUNTS.next()) {//如果对象中有数据，就会循环打印出来
                count = COUNTS.getInt("cOUNTS");
            }
            List<String> liststr = new ArrayList<>();
            liststr.add("10536");
            liststr.add("6");
            liststr.add("301");
            liststr.add("315");
            liststr.add("5005");
            liststr.add("5006");
            liststr.add("5007");
            liststr.add("5008");
            liststr.add("401");
            liststr.add("403");
            liststr.add("402");
            liststr.add("313");
            liststr.add("500001");
            liststr.add("32721");
            liststr.add("32727");
            liststr.add("32730");
            liststr.add("36388");
            liststr.add("36392");
            liststr.add("80064");
            liststr.add("20055");
            liststr.add("20056");
            liststr.add("37000");
            liststr.add("1042");
            liststr.add("1040");
            liststr.add("1041");
            liststr.add("20603");
            liststr.add("37500");
            liststr.add("47555");
            liststr.add("47556");
            liststr.add("40000");
            liststr.add("40001");
            liststr.add("40002");
            liststr.add("40003");
            liststr.add("40004");
            liststr.add("40005");
            liststr.add("40006");
            liststr.add("40304");
            liststr.add("40305");
            liststr.add("40378");
            liststr.add("40379");
            liststr.add("40528");
            liststr.add("41110");
            liststr.add("65302");
            liststr.add("65303");
            liststr.add("65304");
            liststr.add("65305");
            liststr.add("65306");
            liststr.add("65301");
            liststr.add("40377");
            liststr.add("10037");
            liststr.add("10038");
            liststr.add("10042");
            liststr.add("10043");
            liststr.add("10044");
            liststr.add("10045");
            liststr.add("10046");
            liststr.add("10047");
            liststr.add("10259");
            liststr.add("10260");
            liststr.add("10261");
            liststr.add("1206");
            liststr.add("10537");
            liststr.add("10540");
            liststr.add("10562");
            liststr.add("10564");
            liststr.add("164036");
            liststr.add("10278");
            liststr.add("304");
            String values = "";
            Integer start;
            if (count == 0) {
                start = 100000;
            } else {
                Integer fDETAILID = null;
                ResultSet FDETAILID = stmt.executeQuery(" select MAX(FDETAILID) as FDETAILID from  T_SEC_USERROLEMAP");
                while (FDETAILID.next()) {//如果对象中有数据，就会循环打印出来
                    fDETAILID = FDETAILID.getInt("fDETAILID");
                }
                start = fDETAILID;
            }
            for (String s : liststr) {
                Integer current = ++start;
                values = values + ",(" + fENTITYID + "," + current + "," + s + ")";
            }
            String valuess = values.substring(1);
            result = stmt.execute("insert into T_SEC_USERROLEMAP values " + valuess);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean startStock(String dataCentreName, String orgId, String startDate) {
        DBUtil dbUtil = new DBUtil();
        Connection conn = dbUtil.getConnection(dataCentreName);
        Boolean result = null;
        try {
            Statement stmt = conn.createStatement();
             result = stmt.execute("insert into dbo.T_BAS_SYSTEMPROFILE values ('STK',"+orgId+",0,'STARTSTOCKDATE','"+startDate+"',0,0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean updateOrgFunctions(String dataCentreName, String orgId) {
        DBUtil dbUtil = new DBUtil();
        Connection conn = dbUtil.getConnection(dataCentreName);
        //3.通过数据库的连接操作数据库，实现增删改查
        Organization organization = new Organization();
        Boolean rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.execute("update T_ORG_ORGANIZATIONS set FORGFUNCTIONS = '101,102,103,104,107,108,109,110,111,112,113,114' WHERE FORGID = "+orgId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
