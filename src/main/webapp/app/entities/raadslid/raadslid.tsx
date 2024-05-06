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

import { getEntities } from './raadslid.reducer';

export const Raadslid = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const raadslidList = useAppSelector(state => state.raadslid.entities);
  const loading = useAppSelector(state => state.raadslid.loading);

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
      <h2 id="raadslid-heading" data-cy="RaadslidHeading">
        Raadslids
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/raadslid/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Raadslid
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {raadslidList && raadslidList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('achternaam')}>
                  Achternaam <FontAwesomeIcon icon={getSortIconByFieldName('achternaam')} />
                </th>
                <th className="hand" onClick={sort('datumaanstelling')}>
                  Datumaanstelling <FontAwesomeIcon icon={getSortIconByFieldName('datumaanstelling')} />
                </th>
                <th className="hand" onClick={sort('datumuittreding')}>
                  Datumuittreding <FontAwesomeIcon icon={getSortIconByFieldName('datumuittreding')} />
                </th>
                <th className="hand" onClick={sort('fractie')}>
                  Fractie <FontAwesomeIcon icon={getSortIconByFieldName('fractie')} />
                </th>
                <th className="hand" onClick={sort('titel')}>
                  Titel <FontAwesomeIcon icon={getSortIconByFieldName('titel')} />
                </th>
                <th className="hand" onClick={sort('voornaam')}>
                  Voornaam <FontAwesomeIcon icon={getSortIconByFieldName('voornaam')} />
                </th>
                <th>
                  Islidvan Raadscommissie <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {raadslidList.map((raadslid, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/raadslid/${raadslid.id}`} color="link" size="sm">
                      {raadslid.id}
                    </Button>
                  </td>
                  <td>{raadslid.achternaam}</td>
                  <td>
                    {raadslid.datumaanstelling ? (
                      <TextFormat type="date" value={raadslid.datumaanstelling} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {raadslid.datumuittreding ? (
                      <TextFormat type="date" value={raadslid.datumuittreding} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{raadslid.fractie}</td>
                  <td>{raadslid.titel}</td>
                  <td>{raadslid.voornaam}</td>
                  <td>
                    {raadslid.islidvanRaadscommissies
                      ? raadslid.islidvanRaadscommissies.map((val, j) => (
                          <span key={j}>
                            <Link to={`/raadscommissie/${val.id}`}>{val.id}</Link>
                            {j === raadslid.islidvanRaadscommissies.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/raadslid/${raadslid.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/raadslid/${raadslid.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/raadslid/${raadslid.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Raadslids found</div>
        )}
      </div>
    </div>
  );
};

export default Raadslid;
