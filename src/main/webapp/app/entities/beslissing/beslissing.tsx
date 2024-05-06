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

import { getEntities } from './beslissing.reducer';

export const Beslissing = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const beslissingList = useAppSelector(state => state.beslissing.entities);
  const loading = useAppSelector(state => state.beslissing.loading);

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
      <h2 id="beslissing-heading" data-cy="BeslissingHeading">
        Beslissings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/beslissing/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Beslissing
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {beslissingList && beslissingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datum')}>
                  Datum <FontAwesomeIcon icon={getSortIconByFieldName('datum')} />
                </th>
                <th className="hand" onClick={sort('opmerkingen')}>
                  Opmerkingen <FontAwesomeIcon icon={getSortIconByFieldName('opmerkingen')} />
                </th>
                <th className="hand" onClick={sort('reden')}>
                  Reden <FontAwesomeIcon icon={getSortIconByFieldName('reden')} />
                </th>
                <th>
                  Betreft Leerling <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Behandelaar Leerplichtambtenaar <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft School <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {beslissingList.map((beslissing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/beslissing/${beslissing.id}`} color="link" size="sm">
                      {beslissing.id}
                    </Button>
                  </td>
                  <td>{beslissing.datum ? <TextFormat type="date" value={beslissing.datum} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{beslissing.opmerkingen}</td>
                  <td>{beslissing.reden}</td>
                  <td>
                    {beslissing.betreftLeerling ? (
                      <Link to={`/leerling/${beslissing.betreftLeerling.id}`}>{beslissing.betreftLeerling.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {beslissing.behandelaarLeerplichtambtenaar ? (
                      <Link to={`/leerplichtambtenaar/${beslissing.behandelaarLeerplichtambtenaar.id}`}>
                        {beslissing.behandelaarLeerplichtambtenaar.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {beslissing.betreftSchool ? (
                      <Link to={`/school/${beslissing.betreftSchool.id}`}>{beslissing.betreftSchool.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/beslissing/${beslissing.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/beslissing/${beslissing.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/beslissing/${beslissing.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Beslissings found</div>
        )}
      </div>
    </div>
  );
};

export default Beslissing;
