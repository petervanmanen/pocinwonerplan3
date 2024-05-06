import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './lstclass-1.reducer';

export const Lstclass1 = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const lstclass1List = useAppSelector(state => state.lstclass1.entities);
  const loading = useAppSelector(state => state.lstclass1.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="lstclass-1-heading" data-cy="Lstclass1Heading">
        Lstclass 1 S
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/lstclass-1/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Lstclass 1
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {lstclass1List && lstclass1List.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('waarde')}>
                  Waarde <FontAwesomeIcon icon={getSortIconByFieldName('waarde')} />
                </th>
                <th className="hand" onClick={sort('dwhrecordid')}>
                  Dwhrecordid <FontAwesomeIcon icon={getSortIconByFieldName('dwhrecordid')} />
                </th>
                <th className="hand" onClick={sort('dwhodsrecordid')}>
                  Dwhodsrecordid <FontAwesomeIcon icon={getSortIconByFieldName('dwhodsrecordid')} />
                </th>
                <th className="hand" onClick={sort('dwhstartdt')}>
                  Dwhstartdt <FontAwesomeIcon icon={getSortIconByFieldName('dwhstartdt')} />
                </th>
                <th className="hand" onClick={sort('dwheinddt')}>
                  Dwheinddt <FontAwesomeIcon icon={getSortIconByFieldName('dwheinddt')} />
                </th>
                <th className="hand" onClick={sort('dwhrunid')}>
                  Dwhrunid <FontAwesomeIcon icon={getSortIconByFieldName('dwhrunid')} />
                </th>
                <th className="hand" onClick={sort('dwhbron')}>
                  Dwhbron <FontAwesomeIcon icon={getSortIconByFieldName('dwhbron')} />
                </th>
                <th className="hand" onClick={sort('dwhlaaddt')}>
                  Dwhlaaddt <FontAwesomeIcon icon={getSortIconByFieldName('dwhlaaddt')} />
                </th>
                <th className="hand" onClick={sort('dwhactueel')}>
                  Dwhactueel <FontAwesomeIcon icon={getSortIconByFieldName('dwhactueel')} />
                </th>
                <th className="hand" onClick={sort('lstclass1id')}>
                  Lstclass 1 Id <FontAwesomeIcon icon={getSortIconByFieldName('lstclass1id')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lstclass1List.map((lstclass1, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/lstclass-1/${lstclass1.id}`} color="link" size="sm">
                      {lstclass1.id}
                    </Button>
                  </td>
                  <td>{lstclass1.waarde}</td>
                  <td>{lstclass1.dwhrecordid}</td>
                  <td>{lstclass1.dwhodsrecordid}</td>
                  <td>
                    {lstclass1.dwhstartdt ? <TextFormat type="date" value={lstclass1.dwhstartdt} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {lstclass1.dwheinddt ? <TextFormat type="date" value={lstclass1.dwheinddt} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{lstclass1.dwhrunid}</td>
                  <td>{lstclass1.dwhbron}</td>
                  <td>
                    {lstclass1.dwhlaaddt ? <TextFormat type="date" value={lstclass1.dwhlaaddt} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{lstclass1.dwhactueel}</td>
                  <td>{lstclass1.lstclass1id}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/lstclass-1/${lstclass1.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/lstclass-1/${lstclass1.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/lstclass-1/${lstclass1.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Lstclass 1 S found</div>
        )}
      </div>
    </div>
  );
};

export default Lstclass1;
