import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './heffing.reducer';

export const Heffing = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const heffingList = useAppSelector(state => state.heffing.entities);
  const loading = useAppSelector(state => state.heffing.loading);

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
      <h2 id="heffing-heading" data-cy="HeffingHeading">
        Heffings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/heffing/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Heffing
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {heffingList && heffingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bedrag')}>
                  Bedrag <FontAwesomeIcon icon={getSortIconByFieldName('bedrag')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  Code <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('datumindiening')}>
                  Datumindiening <FontAwesomeIcon icon={getSortIconByFieldName('datumindiening')} />
                </th>
                <th className="hand" onClick={sort('gefactureerd')}>
                  Gefactureerd <FontAwesomeIcon icon={getSortIconByFieldName('gefactureerd')} />
                </th>
                <th className="hand" onClick={sort('inrekening')}>
                  Inrekening <FontAwesomeIcon icon={getSortIconByFieldName('inrekening')} />
                </th>
                <th className="hand" onClick={sort('nummer')}>
                  Nummer <FontAwesomeIcon icon={getSortIconByFieldName('nummer')} />
                </th>
                <th className="hand" onClick={sort('runnummer')}>
                  Runnummer <FontAwesomeIcon icon={getSortIconByFieldName('runnummer')} />
                </th>
                <th>
                  Heeftgrondslag Heffinggrondslag <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {heffingList.map((heffing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/heffing/${heffing.id}`} color="link" size="sm">
                      {heffing.id}
                    </Button>
                  </td>
                  <td>{heffing.bedrag}</td>
                  <td>{heffing.code}</td>
                  <td>{heffing.datumindiening}</td>
                  <td>{heffing.gefactureerd ? 'true' : 'false'}</td>
                  <td>{heffing.inrekening}</td>
                  <td>{heffing.nummer}</td>
                  <td>{heffing.runnummer}</td>
                  <td>
                    {heffing.heeftgrondslagHeffinggrondslag ? (
                      <Link to={`/heffinggrondslag/${heffing.heeftgrondslagHeffinggrondslag.id}`}>
                        {heffing.heeftgrondslagHeffinggrondslag.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/heffing/${heffing.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/heffing/${heffing.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/heffing/${heffing.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Heffings found</div>
        )}
      </div>
    </div>
  );
};

export default Heffing;
