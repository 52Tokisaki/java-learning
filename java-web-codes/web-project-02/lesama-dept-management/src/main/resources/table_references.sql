- 为emp表添加外键约束
ALTER TABLE emp ADD CONSTRAINT fk_emp_dept_id FOREIGN KEY(dept_id) REFERENCES dept(id);

- 删除emp到dept表的约束
ALTER TABLE emp DROP CONSTRAINT fk_emp_dept_id;


-- 多表查询
select * from dept, emp where emp.dept_id = dept.id;

-- 查询所有员工的ID，姓名，及所属的部门名称 （显式内连接）
SELECT emp.id, emp.name, dept.name FROM emp INNER JOIN dept ON emp.dept_id = dept.id ORDER BY emp.id DESC;

-- 查询 性别为男, 且工资 高于8000 的员工的ID, 姓名, 及所属的部门名称 （隐式内连接）
SELECT emp.id, emp.name, dept.name FROM emp, dept WHERE emp.dept_id = dept.id and emp.gender = 1;

-- 查询员工表 所有 员工的姓名, 和对应的部门名称 (左外连接)
SELECT emp.name, dept.name FROM emp LEFT JOIN dept ON emp.dept_id = dept.id;

-- 查询部门表 所有 部门的名称, 和对应的员工名称 (右外连接)
SELECT d.name, e.name FROM emp e RIGHT JOIN dept d ON e.dept_id = d.id;

-- 查询工资 高于8000 的 所有员工的姓名, 和对应的部门名称 (左外连接)
SELECT e.name, d.name FROM emp e LEFT JOIN dept d ON e.dept_id = d.id WHERE e.salary > 8000;

-- 查询 最早入职 的员工信息
SELECT * FROM emp WHERE emp.entry_date = (SELECT MIN(emp.entry_date) FROM emp);

-- 查询在 阮小五 入职之后入职的员工信息
SELECT * FROM emp WHERE emp.entry_date > (SELECT emp.entry_date FROM emp WHERE emp.name = '阮小五');

-- 查询 "教研部" 和 "咨询部" 的所有员工信息
SELECT * FROM emp WHERE dept_id in (SELECT id FROM dept WHERE name in ('教研部', '咨询部'));

-- 查询与 "李忠" 的薪资 及 职位都相同的员工信息
SELECT * FROM emp WHERE (salary, job) = (SELECT salary, job FROM emp WHERE name = '李忠');

-- 获取每个部门中薪资最高的员工信息
SELECT e.dept_id, MAX(e.salary) FROM emp e, dept d WHERE e.dept_id = d.id GROUP BY e.dept_id;
SELECT * FROM emp, (SELECT e.dept_id, MAX(e.salary) max_salary FROM emp e, dept d WHERE e.dept_id = d.id GROUP BY e.dept_id) a
WHERE emp.dept_id = a.dept_id and emp.salary = max_salary;

-- 查询 "教研部" 性别为 男，且在 "2011-05-01" 之后入职的员工信息 。
SELECT e.* FROM emp e, dept d WHERE e.dept_id = d.id and
        d.name = '教研部' and e.gender = 1 and e.entry_date > '2011-05-01';

-- 查询工资 低于公司平均工资的 且 性别为男 的员工信息 。
SELECT * FROM emp WHERE gender = 1 and salary > (SELECT AVG(salary) FROM emp);

-- 查询部门人数超过 10 人的部门名称 。
SELECT d.name, count(*) group_count FROM emp e, dept d WHERE e.dept_id = d.id GROUP BY d.id HAVING group_count > 10;

-- 查询在 "2010-05-01" 后入职，且薪资高于 10000 的 "教研部" 员工信息，并根据薪资倒序排序。
SELECT * FROM emp e, dept d WHERE e.dept_id = d.id and e.entry_date > '2010-05-01' and e.salary > 10000
                              and d.name = '教研部' ORDER BY e.salary DESC;
-- 查询工资 低于本部门平均工资的员工信息
SELECT e.* FROM emp e, (SELECT d.id, AVG(e.salary) avg_salary FROM emp e, dept d WHERE e.dept_id = d.id GROUP BY d.id) a
WHERE e.salary < a.avg_salary;

-- 开启事务
start transaction;

-- 1.添加员工
insert into emp values (40, 'Tom', '123456', '汤姆', 1, '13300001111', 1, 4000, '1.jpg', '2023-11-01', 1, now(), now());

-- 2.添加员工工作经历
insert into emp_expr(emp_id, begin, end, company, job) values (40,'2019-01-01', '2020-01-01', '百度', '开发'),(40,'2020-01-10', '2022-02-01', '阿里', '架构');
-- 提交事务(全部成功)
commit;
-- 回滚事务(有一个失败)
rollback;

select * from emp;
select * from emp_expr;