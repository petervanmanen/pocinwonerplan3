import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './parkeerzone.reducer';

export const Parkeerzone = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const parkeerzoneList = useAppSelector(state => state.parkeerzone.entities);
  const loading = useAppSelector(state => state.parkeerzone.loading);

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
      <h2 id="parkeerzone-heading" data-cy="ParkeerzoneHeading">
        Parkeerzones
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/parkeerzone/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Parkeerzone
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {parkeerzoneList && parkeerzoneList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantalparkeervlakken')}>
                  Aantalparkeervlakken <FontAwesomeIcon icon={getSortIconByFieldName('aantalparkeervlakken')} />
                </th>
                <th className="hand" onClick={sort('alleendagtarief')}>
                  Alleendagtarief <FontAwesomeIcon icon={getSortIconByFieldName('alleendagtarief')} />
                </th>
                <th className="hand" onClick={sort('dagtarief')}>
                  Dagtarief <FontAwesomeIcon icon={getSortIconByFieldName('dagtarief')} />
                </th>
                <th className="hand" onClick={sort('eindedag')}>
                  Eindedag <FontAwesomeIcon icon={getSortIconByFieldName('eindedag')} />
                </th>
                <th className="hand" onClick={sort('eindtijd')}>
                  Eindtijd <FontAwesomeIcon icon={getSortIconByFieldName('eindtijd')} />
                </th>
                <th className="hand" onClick={sort('gebruik')}>
                  Gebruik <FontAwesomeIcon icon={getSortIconByFieldName('gebruik')} />
                </th>
                <th className="hand" onClick={sort('geometrie')}>
                  Geometrie <FontAwesomeIcon icon={getSortIconByFieldName('geometrie')} />
                </th>
                <th className="hand" onClick={sort('ipmcode')}>
                  Ipmcode <FontAwesomeIcon icon={getSortIconByFieldName('ipmcode')} />
                </th>
                <th className="hand" onClick={sort('ipmnaam')}>
                  Ipmnaam <FontAwesomeIcon icon={getSortIconByFieldName('ipmnaam')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('parkeergarage')}>
                  Parkeergarage <FontAwesomeIcon icon={getSortIconByFieldName('parkeergarage')} />
                </th>
                <th className="hand" onClick={sort('sectorcode')}>
                  Sectorcode <FontAwesomeIcon icon={getSortIconByFieldName('sectorcode')} />
                </th>
                <th className="hand" onClick={sort('soortcode')}>
                  Soortcode <FontAwesomeIcon icon={getSortIconByFieldName('soortcode')} />
                </th>
                <th className="hand" onClick={sort('startdag')}>
                  Startdag <FontAwesomeIcon icon={getSortIconByFieldName('startdag')} />
                </th>
                <th className="hand" onClick={sort('starttarief')}>
                  Starttarief <FontAwesomeIcon icon={getSortIconByFieldName('starttarief')} />
                </th>
                <th className="hand" onClick={sort('starttijd')}>
                  Starttijd <FontAwesomeIcon icon={getSortIconByFieldName('starttijd')} />
                </th>
                <th className="hand" onClick={sort('typecode')}>
                  Typecode <FontAwesomeIcon icon={getSortIconByFieldName('typecode')} />
                </th>
                <th className="hand" onClick={sort('typenaam')}>
                  Typenaam <FontAwesomeIcon icon={getSortIconByFieldName('typenaam')} />
                </th>
                <th className="hand" onClick={sort('uurtarief')}>
                  Uurtarief <FontAwesomeIcon icon={getSortIconByFieldName('uurtarief')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {parkeerzoneList.map((parkeerzone, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/parkeerzone/${parkeerzone.id}`} color="link" size="sm">
                      {parkeerzone.id}
                    </Button>
                  </td>
                  <td>{parkeerzone.aantalparkeervlakken}</td>
                  <td>{parkeerzone.alleendagtarief ? 'true' : 'false'}</td>
                  <td>{parkeerzone.dagtarief}</td>
                  <td>{parkeerzone.eindedag}</td>
                  <td>{parkeerzone.eindtijd}</td>
                  <td>{parkeerzone.gebruik}</td>
                  <td>{parkeerzone.geometrie}</td>
                  <td>{parkeerzone.ipmcode}</td>
                  <td>{parkeerzone.ipmnaam}</td>
                  <td>{parkeerzone.naam}</td>
                  <td>{parkeerzone.parkeergarage ? 'true' : 'false'}</td>
                  <td>{parkeerzone.sectorcode}</td>
                  <td>{parkeerzone.soortcode}</td>
                  <td>{parkeerzone.startdag}</td>
                  <td>{parkeerzone.starttarief}</td>
                  <td>{parkeerzone.starttijd}</td>
                  <td>{parkeerzone.typecode}</td>
                  <td>{parkeerzone.typenaam}</td>
                  <td>{parkeerzone.uurtarief}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/parkeerzone/${parkeerzone.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/parkeerzone/${parkeerzone.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/parkeerzone/${parkeerzone.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Parkeerzones found</div>
        )}
      </div>
    </div>
  );
};

export default Parkeerzone;
