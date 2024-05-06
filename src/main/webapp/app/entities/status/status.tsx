import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './status.reducer';

export const Status = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const statusList = useAppSelector(state => state.status.entities);
  const loading = useAppSelector(state => state.status.loading);

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
      <h2 id="status-heading" data-cy="StatusHeading">
        Statuses
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/status/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Status
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {statusList && statusList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumstatusgezet')}>
                  Datumstatusgezet <FontAwesomeIcon icon={getSortIconByFieldName('datumstatusgezet')} />
                </th>
                <th className="hand" onClick={sort('indicatieiaatstgezettestatus')}>
                  Indicatieiaatstgezettestatus <FontAwesomeIcon icon={getSortIconByFieldName('indicatieiaatstgezettestatus')} />
                </th>
                <th className="hand" onClick={sort('statustoelichting')}>
                  Statustoelichting <FontAwesomeIcon icon={getSortIconByFieldName('statustoelichting')} />
                </th>
                <th>
                  Isvan Statustype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Zaak <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {statusList.map((status, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/status/${status.id}`} color="link" size="sm">
                      {status.id}
                    </Button>
                  </td>
                  <td>{status.datumstatusgezet}</td>
                  <td>{status.indicatieiaatstgezettestatus}</td>
                  <td>{status.statustoelichting}</td>
                  <td>
                    {status.isvanStatustype ? <Link to={`/statustype/${status.isvanStatustype.id}`}>{status.isvanStatustype.id}</Link> : ''}
                  </td>
                  <td>{status.heeftZaak ? <Link to={`/zaak/${status.heeftZaak.id}`}>{status.heeftZaak.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/status/${status.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/status/${status.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/status/${status.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Statuses found</div>
        )}
      </div>
    </div>
  );
};

export default Status;
