import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './verzuim.reducer';

export const Verzuim = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verzuimList = useAppSelector(state => state.verzuim.entities);
  const loading = useAppSelector(state => state.verzuim.loading);

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
      <h2 id="verzuim-heading" data-cy="VerzuimHeading">
        Verzuims
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/verzuim/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verzuim
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verzuimList && verzuimList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumtijdeinde')}>
                  Datumtijdeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumtijdeinde')} />
                </th>
                <th className="hand" onClick={sort('datumtijdstart')}>
                  Datumtijdstart <FontAwesomeIcon icon={getSortIconByFieldName('datumtijdstart')} />
                </th>
                <th>
                  Soortverzuim Verzuimsoort <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftverzuim Werknemer <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verzuimList.map((verzuim, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verzuim/${verzuim.id}`} color="link" size="sm">
                      {verzuim.id}
                    </Button>
                  </td>
                  <td>{verzuim.datumtijdeinde}</td>
                  <td>{verzuim.datumtijdstart}</td>
                  <td>
                    {verzuim.soortverzuimVerzuimsoort ? (
                      <Link to={`/verzuimsoort/${verzuim.soortverzuimVerzuimsoort.id}`}>{verzuim.soortverzuimVerzuimsoort.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {verzuim.heeftverzuimWerknemer ? (
                      <Link to={`/werknemer/${verzuim.heeftverzuimWerknemer.id}`}>{verzuim.heeftverzuimWerknemer.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/verzuim/${verzuim.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/verzuim/${verzuim.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verzuim/${verzuim.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verzuims found</div>
        )}
      </div>
    </div>
  );
};

export default Verzuim;
