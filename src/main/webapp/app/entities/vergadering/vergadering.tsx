import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vergadering.reducer';

export const Vergadering = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vergaderingList = useAppSelector(state => state.vergadering.entities);
  const loading = useAppSelector(state => state.vergadering.loading);

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
      <h2 id="vergadering-heading" data-cy="VergaderingHeading">
        Vergaderings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vergadering/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vergadering
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vergaderingList && vergaderingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('eindtijd')}>
                  Eindtijd <FontAwesomeIcon icon={getSortIconByFieldName('eindtijd')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('starttijd')}>
                  Starttijd <FontAwesomeIcon icon={getSortIconByFieldName('starttijd')} />
                </th>
                <th className="hand" onClick={sort('titel')}>
                  Titel <FontAwesomeIcon icon={getSortIconByFieldName('titel')} />
                </th>
                <th>
                  Heeftverslag Raadsstuk <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Raadscommissie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Wordtbehandeldin Raadsstuk <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vergaderingList.map((vergadering, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vergadering/${vergadering.id}`} color="link" size="sm">
                      {vergadering.id}
                    </Button>
                  </td>
                  <td>{vergadering.eindtijd}</td>
                  <td>{vergadering.locatie}</td>
                  <td>{vergadering.starttijd}</td>
                  <td>{vergadering.titel}</td>
                  <td>
                    {vergadering.heeftverslagRaadsstuk ? (
                      <Link to={`/raadsstuk/${vergadering.heeftverslagRaadsstuk.id}`}>{vergadering.heeftverslagRaadsstuk.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vergadering.heeftRaadscommissie ? (
                      <Link to={`/raadscommissie/${vergadering.heeftRaadscommissie.id}`}>{vergadering.heeftRaadscommissie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vergadering.wordtbehandeldinRaadsstuks
                      ? vergadering.wordtbehandeldinRaadsstuks.map((val, j) => (
                          <span key={j}>
                            <Link to={`/raadsstuk/${val.id}`}>{val.id}</Link>
                            {j === vergadering.wordtbehandeldinRaadsstuks.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vergadering/${vergadering.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vergadering/${vergadering.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vergadering/${vergadering.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vergaderings found</div>
        )}
      </div>
    </div>
  );
};

export default Vergadering;
