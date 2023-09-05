import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import type { TablePaginationConfig } from 'antd'
import { Button, Popconfirm, Space, Table, message } from 'antd'
import useSWR from 'swr'
import type { AxiosError } from 'axios'
import type { ColumnsType } from 'antd/es/table'
import type { HttpResponse, L } from '../../lib/Http.tsx'
import { http } from '../../lib/Http.tsx'

export const AdminContestPage: React.FC = () => {
  const nav = useNavigate()
  const [pagination, setPagination] = useState<TablePaginationConfig>({
    current: 1,
    pageSize: 20,
    showTotal: total => `共 ${total} 条`,
    showSizeChanger: true,
    onChange: (current, pageSize) => {
      setPagination({
        ...pagination,
        current,
        pageSize,
      })
    },
  })

  const {
    data,
    error,
    mutate,
  } = useSWR(`/admin/contest?current=${pagination.current ?? 1}&pageSize=${pagination.pageSize ?? 20}`, async (path) => {
    return http.get<L<AdminDto.Contest>>(path)
      .then((res) => {
        setPagination({
          ...pagination,
          total: res.data.data.total,
        })
        return res.data.data.list
      })
      .catch((err: AxiosError<HttpResponse>) => {
        void message.error(err.response?.data.message ?? '获取竞赛列表失败')
        throw err
      })
  })
  const loading = !data && !error
  const deleteContest = (id: number) => {
    http.delete(`/admin/contest/${id}`)
      .then(() => {
        void message.success('删除成功')
        void mutate()
      })
      .catch((err: AxiosError<HttpResponse>) => {
        void message.error(err.response?.data.message ?? '删除失败')
        throw err
      })
  }

  const columns: ColumnsType<AdminDto.Contest> = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: '标题',
      dataIndex: 'title',
      key: 'title',
      render: (value, record) => {
        return <Link to={`/c/${record.id ?? 0}`}>{value}</Link>
      },
    },
    {
      title: '创建人',
      dataIndex: 'author',
      key: 'author',
    },
    {
      title: '开始时间',
      dataIndex: 'startTime',
      key: 'startTime',
    },
    {
      title: '结束时间',
      dataIndex: 'endTime',
      key: 'endTime',
    },
    {
      title: '是否可见',
      dataIndex: 'visible',
      key: 'visible',
      render: (value: boolean) => {
        return (value ? '是' : '否')
      },
    },
    {
      title: '操作',
      dataIndex: 'id',
      render: (value: number) => {
        return (
          <Space>
            <Button
              type="link"
              size='small'
              onClick={() => {
                nav(`/admin/contest/${value ?? 0}/edit`)
              }}
            >编辑</Button>
            <Popconfirm
              title="删除"
              description="确认删除这一项吗？"
              onConfirm={() => deleteContest(value ?? 0)}
              okText="确认"
              cancelText="取消"
            >
              <Button type="link">删除</Button>
            </Popconfirm>
          </Space>
        )
      },
    },
  ]
  return (
    <div flex flex-col p-4>
      <h2 mb-2>竞赛管理</h2>
      <Space direction='vertical'>
        <div flex justify-between>
          <div>
            <button onClick={() => nav('/admin/contest/new')}>新建</button>
          </div>
        </div>
        <div>
          <Table
            dataSource={data}
            columns={columns}
            rowKey="id"
            loading={loading}
            pagination={pagination}/>
        </div>
      </Space>
    </div>
  )
}

export default AdminContestPage
