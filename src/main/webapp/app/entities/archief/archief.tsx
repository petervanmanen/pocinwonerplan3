import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './archief.reducer';

export const Archief = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const archiefList = useAppSelector(state => state.archief.entities);
  const loading = useAppSelector(state => state.archief.loading);

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
      <h2 id="archief-heading" data-cy="ArchiefHeading">
        Archiefs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/archief/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Archief
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {archiefList && archiefList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('archiefnummer')}>
                  Archiefnummer <FontAwesomeIcon icon={getSortIconByFieldName('archiefnummer')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('openbaarheidsbeperking')}>
                  Openbaarheidsbeperking <FontAwesomeIcon icon={getSortIconByFieldName('openbaarheidsbeperking')} />
                </th>
                <th>
                  Heeft Rechthebbende <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Valtbinnen Archiefcategorie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Stamtuit Periode <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {archiefList.map((archief, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/archief/${archief.id}`} color="link" size="sm">
                      {archief.id}
                    </Button>
                  </td>
                  <td>{archief.archiefnummer}</td>
                  <td>{archief.naam}</td>
                  <td>{archief.omschrijving}</td>
                  <td>{archief.openbaarheidsbeperking}</td>
                  <td>
                    {archief.heeftRechthebbende ? (
                      <Link to={`/rechthebbende/${archief.heeftRechthebbende.id}`}>{archief.heeftRechthebbende.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {archief.valtbinnenArchiefcategories
                      ? archief.valtbinnenArchiefcategories.map((val, j) => (
                          <span key={j}>
                            <Link to={`/archiefcategorie/${val.id}`}>{val.id}</Link>
                            {j === archief.valtbinnenArchiefcategories.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {archief.stamtuitPeriodes
                      ? archief.stamtuitPeriodes.map((val, j) => (
                          <span key={j}>
                            <Link to={`/periode/${val.id}`}>{val.id}</Link>
                            {j === archief.stamtuitPeriodes.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/archief/${archief.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/archief/${archief.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/archief/${archief.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Archiefs found</div>
        )}
      </div>
    </div>
  );
};

export default Archief;
