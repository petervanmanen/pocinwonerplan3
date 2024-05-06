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

import { getEntities } from './wozwaarde.reducer';

export const Wozwaarde = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const wozwaardeList = useAppSelector(state => state.wozwaarde.entities);
  const loading = useAppSelector(state => state.wozwaarde.loading);

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
      <h2 id="wozwaarde-heading" data-cy="WozwaardeHeading">
        Wozwaardes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/wozwaarde/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Wozwaarde
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {wozwaardeList && wozwaardeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumpeilingtoestand')}>
                  Datumpeilingtoestand <FontAwesomeIcon icon={getSortIconByFieldName('datumpeilingtoestand')} />
                </th>
                <th className="hand" onClick={sort('datumwaardepeiling')}>
                  Datumwaardepeiling <FontAwesomeIcon icon={getSortIconByFieldName('datumwaardepeiling')} />
                </th>
                <th className="hand" onClick={sort('statusbeschikking')}>
                  Statusbeschikking <FontAwesomeIcon icon={getSortIconByFieldName('statusbeschikking')} />
                </th>
                <th className="hand" onClick={sort('vastgesteldewaarde')}>
                  Vastgesteldewaarde <FontAwesomeIcon icon={getSortIconByFieldName('vastgesteldewaarde')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {wozwaardeList.map((wozwaarde, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/wozwaarde/${wozwaarde.id}`} color="link" size="sm">
                      {wozwaarde.id}
                    </Button>
                  </td>
                  <td>
                    {wozwaarde.datumpeilingtoestand ? (
                      <TextFormat type="date" value={wozwaarde.datumpeilingtoestand} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {wozwaarde.datumwaardepeiling ? (
                      <TextFormat type="date" value={wozwaarde.datumwaardepeiling} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{wozwaarde.statusbeschikking}</td>
                  <td>{wozwaarde.vastgesteldewaarde}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/wozwaarde/${wozwaarde.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/wozwaarde/${wozwaarde.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/wozwaarde/${wozwaarde.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Wozwaardes found</div>
        )}
      </div>
    </div>
  );
};

export default Wozwaarde;
