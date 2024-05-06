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

import { getEntities } from './verlof.reducer';

export const Verlof = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verlofList = useAppSelector(state => state.verlof.entities);
  const loading = useAppSelector(state => state.verlof.loading);

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
      <h2 id="verlof-heading" data-cy="VerlofHeading">
        Verlofs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/verlof/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verlof
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verlofList && verlofList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumaanvraag')}>
                  Datumaanvraag <FontAwesomeIcon icon={getSortIconByFieldName('datumaanvraag')} />
                </th>
                <th className="hand" onClick={sort('datumtijdeinde')}>
                  Datumtijdeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumtijdeinde')} />
                </th>
                <th className="hand" onClick={sort('datumtijdstart')}>
                  Datumtijdstart <FontAwesomeIcon icon={getSortIconByFieldName('datumtijdstart')} />
                </th>
                <th className="hand" onClick={sort('datumtoekenning')}>
                  Datumtoekenning <FontAwesomeIcon icon={getSortIconByFieldName('datumtoekenning')} />
                </th>
                <th className="hand" onClick={sort('goedgekeurd')}>
                  Goedgekeurd <FontAwesomeIcon icon={getSortIconByFieldName('goedgekeurd')} />
                </th>
                <th>
                  Soortverlof Verlofsoort <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftverlof Werknemer <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verlofList.map((verlof, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verlof/${verlof.id}`} color="link" size="sm">
                      {verlof.id}
                    </Button>
                  </td>
                  <td>
                    {verlof.datumaanvraag ? <TextFormat type="date" value={verlof.datumaanvraag} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{verlof.datumtijdeinde}</td>
                  <td>{verlof.datumtijdstart}</td>
                  <td>
                    {verlof.datumtoekenning ? (
                      <TextFormat type="date" value={verlof.datumtoekenning} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{verlof.goedgekeurd ? 'true' : 'false'}</td>
                  <td>
                    {verlof.soortverlofVerlofsoort ? (
                      <Link to={`/verlofsoort/${verlof.soortverlofVerlofsoort.id}`}>{verlof.soortverlofVerlofsoort.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {verlof.heeftverlofWerknemer ? (
                      <Link to={`/werknemer/${verlof.heeftverlofWerknemer.id}`}>{verlof.heeftverlofWerknemer.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/verlof/${verlof.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/verlof/${verlof.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verlof/${verlof.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verlofs found</div>
        )}
      </div>
    </div>
  );
};

export default Verlof;
