import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './telefoontje.reducer';

export const Telefoontje = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const telefoontjeList = useAppSelector(state => state.telefoontje.entities);
  const loading = useAppSelector(state => state.telefoontje.loading);

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
      <h2 id="telefoontje-heading" data-cy="TelefoontjeHeading">
        Telefoontjes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/telefoontje/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Telefoontje
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {telefoontjeList && telefoontjeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('afhandeltijdnagesprek')}>
                  Afhandeltijdnagesprek <FontAwesomeIcon icon={getSortIconByFieldName('afhandeltijdnagesprek')} />
                </th>
                <th className="hand" onClick={sort('deltaisdnconnectie')}>
                  Deltaisdnconnectie <FontAwesomeIcon icon={getSortIconByFieldName('deltaisdnconnectie')} />
                </th>
                <th className="hand" onClick={sort('eindtijd')}>
                  Eindtijd <FontAwesomeIcon icon={getSortIconByFieldName('eindtijd')} />
                </th>
                <th className="hand" onClick={sort('starttijd')}>
                  Starttijd <FontAwesomeIcon icon={getSortIconByFieldName('starttijd')} />
                </th>
                <th className="hand" onClick={sort('totaleonholdtijd')}>
                  Totaleonholdtijd <FontAwesomeIcon icon={getSortIconByFieldName('totaleonholdtijd')} />
                </th>
                <th className="hand" onClick={sort('totalespreektijd')}>
                  Totalespreektijd <FontAwesomeIcon icon={getSortIconByFieldName('totalespreektijd')} />
                </th>
                <th className="hand" onClick={sort('totalewachttijd')}>
                  Totalewachttijd <FontAwesomeIcon icon={getSortIconByFieldName('totalewachttijd')} />
                </th>
                <th className="hand" onClick={sort('totlatetijdsduur')}>
                  Totlatetijdsduur <FontAwesomeIcon icon={getSortIconByFieldName('totlatetijdsduur')} />
                </th>
                <th className="hand" onClick={sort('trackid')}>
                  Trackid <FontAwesomeIcon icon={getSortIconByFieldName('trackid')} />
                </th>
                <th>
                  Heeft Telefoonstatus <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Telefoononderwerp <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {telefoontjeList.map((telefoontje, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/telefoontje/${telefoontje.id}`} color="link" size="sm">
                      {telefoontje.id}
                    </Button>
                  </td>
                  <td>{telefoontje.afhandeltijdnagesprek}</td>
                  <td>{telefoontje.deltaisdnconnectie}</td>
                  <td>{telefoontje.eindtijd}</td>
                  <td>{telefoontje.starttijd}</td>
                  <td>{telefoontje.totaleonholdtijd}</td>
                  <td>{telefoontje.totalespreektijd}</td>
                  <td>{telefoontje.totalewachttijd}</td>
                  <td>{telefoontje.totlatetijdsduur}</td>
                  <td>{telefoontje.trackid}</td>
                  <td>
                    {telefoontje.heeftTelefoonstatus ? (
                      <Link to={`/telefoonstatus/${telefoontje.heeftTelefoonstatus.id}`}>{telefoontje.heeftTelefoonstatus.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {telefoontje.heeftTelefoononderwerp ? (
                      <Link to={`/telefoononderwerp/${telefoontje.heeftTelefoononderwerp.id}`}>
                        {telefoontje.heeftTelefoononderwerp.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/telefoontje/${telefoontje.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/telefoontje/${telefoontje.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/telefoontje/${telefoontje.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Telefoontjes found</div>
        )}
      </div>
    </div>
  );
};

export default Telefoontje;
