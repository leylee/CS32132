/**
 * @author: 1190501001 李恩宇
 * @build_command: g++ -std=c++11 path/to/source.cpp
 * @description:参加比赛有n个学院, 学院编号为1......n. 比赛分成 m 个男子项目, 和
 * w 个女子项目. 项目编号为男子 1......m, 女子 m+1......m+w.
 * 不同的项目取前五名或前三名积分; 取前五名的积分分别为:7, 5, 3, 2, 1,
 * 前三名的积分分别为: 5, 3, 2; 哪些取前五名或前三名由学生自己设定. (m<=20,
 * n<=20) 功能要求: 1) 可以输入各个项目的前三名或前五名的成绩; 2)
 * 能统计各学院总分; 3) 可以按学院编号或名称、学院总分、男女团体总分排序输出; 4)
 * 可以按学院编号查询学院某个项目的情况; 可以按项目编号查询取得前三
 * 或前五名的学院.  5) 数据存入文件并能随时查询; 6)
 * 规定: 输入数据形式和范围:可以输入学院的名称, 运动项目的名称
 * 输出形式:有中文提示, 各学院分数为整形 界面要求: 有合理的提示,
 * 每个功能可以设立菜单, 根据提示, 可以完成相关的功能要求. 存储结构:
 * 学生自己根据系统功能要求自己设计, 但是要求运动会的相关数据要存储在数据文件中.
 * 测试数据: 1. 全部合法数据; 2. 整体非法数据; 3. 局部非法数据. 进行程序测试,
 * 以保证程序的稳定. 测试数据及测试结果请在上交的资料中写明;
 * @input:
 * @output:
 * @hint: Solution 类是算法核心.
 */

#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Solution {
 private:
  class Event;
  class School;
  static vector<int> is_first_3;
  static vector<Event> events;
  static vector<School> schools;

  class Event {
   private:
    int podiumNumber = 0;
    constexpr static int five_scores[6] = {0, 7, 5, 3, 2, 1};
    constexpr static int three_scores[4] = {0, 5, 3, 2};
    int eventId;
    vector<int> rankedSchoolIds;

   public:
    inline bool setPodiumNumber(int number) {
      if (number == 3 || number == 5) {
        podiumNumber = number;
        rankedSchoolIds.resize(number + 1, 0);
        return true;
      } else {
        return false;
      }
    }

    inline int setEventId(int id) { eventId = id; }

    inline int getScoreAtRank(int rank) const {
      if (podiumNumber == 3) {
        return three_scores[rank];
      } else if (podiumNumber == 5) {
        return five_scores[rank];
      } else {
        return 0;
      }
    }
  };

  class School {
   private:
    vector<int> ranks;
    vector<int> rankedGameIds;
    int m, w;
    int score = 0, mscore = 0, wscore = 0;
    int schoolId;
    string name;

   public:
    enum { SCHOOL_ID, NAME, SCORE, MSCORE, WSCORE };

    School() {}
    // School(int _m, int _w) { configure(_m, _w); }

    inline void configure(int _m, int _w) {
      m = _m;
      w = _w;
      rankedGameIds.clear();
      ranks.clear();
      ranks.resize(_m + _w + 1, 0);
    }

    /**
     * 增加学院得分
     * int game: 项目编号;
     * int rank: 项目排名, 1-5 or 1-3
     */
    void goal(int eventId, int rank) {
      rankedGameIds.push_back(eventId);
      ranks[eventId] = rank;
      int scoreAtRank = events[eventId].getScoreAtRank(rank);
      score += scoreAtRank;
      if (eventId > m) {
        wscore += scoreAtRank;
      } else {
        mscore += scoreAtRank;
      }
    }

    static inline bool cmpScore(const School& a, const School& b) {
      return a.score > b.score;
    }
    static inline bool cmpMscore(const School& a, const School& b) {
      return a.mscore > b.mscore;
    }
    static inline bool cmpWscore(const School& a, const School& b) {
      return a.wscore > b.wscore;
    }
    static inline bool cmpSchoolId(const School& a, const School& b) {
      return a.schoolId < b.schoolId;
    }
    static inline bool cmpName(const School& a, const School& b) {
      return a.name < b.name;
    }

    static bool (*getCmp(int a))(const School&, const School&) {
      static bool (*cmpList[])(const School&, const School&) = {
          [SCHOOL_ID] = cmpSchoolId,
          [NAME] = cmpName,
          [SCORE] = cmpScore,
          [MSCORE] = cmpMscore,
          [WSCORE] = cmpWscore};
      return cmpList[a];
    }
  };

 public:
  void solve() {}
};

int main() { int n, m, w; }