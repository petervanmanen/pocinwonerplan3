import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './paal.reducer';

export const Paal = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const paalList = useAppSelector(state => state.paal.entities);
  const loading = useAppSelector(state => state.paal.loading);

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
      <h2 id="paal-heading" data-cy="PaalHeading">
        Paals
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/paal/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Paal
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {paalList && paalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('breedte')}>
                  Breedte <FontAwesomeIcon icon={getSortIconByFieldName('breedte')} />
                </th>
                <th className="hand" onClick={sort('diameter')}>
                  Diameter <FontAwesomeIcon icon={getSortIconByFieldName('diameter')} />
                </th>
                <th className="hand" onClick={sort('hoogte')}>
                  Hoogte <FontAwesomeIcon icon={getSortIconByFieldName('hoogte')} />
                </th>
                <th className="hand" onClick={sort('jaaronderhouduitgevoerd')}>
                  Jaaronderhouduitgevoerd <FontAwesomeIcon icon={getSortIconByFieldName('jaaronderhouduitgevoerd')} />
                </th>
                <th className="hand" onClick={sort('kwaliteitsniveauactueel')}>
                  Kwaliteitsniveauactueel <FontAwesomeIcon icon={getSortIconByFieldName('kwaliteitsniveauactueel')} />
                </th>
                <th className="hand" onClick={sort('kwaliteitsniveaugewenst')}>
                  Kwaliteitsniveaugewenst <FontAwesomeIcon icon={getSortIconByFieldName('kwaliteitsniveaugewenst')} />
                </th>
                <th className="hand" onClick={sort('lengte')}>
                  Lengte <FontAwesomeIcon icon={getSortIconByFieldName('lengte')} />
                </th>
                <th className="hand" onClick={sort('leverancier')}>
                  Leverancier <FontAwesomeIcon icon={getSortIconByFieldName('leverancier')} />
                </th>
                <th className="hand" onClick={sort('materiaal')}>
                  Materiaal <FontAwesomeIcon icon={getSortIconByFieldName('materiaal')} />
                </th>
                <th className="hand" onClick={sort('vorm')}>
                  Vorm <FontAwesomeIcon icon={getSortIconByFieldName('vorm')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {paalList.map((paal, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/paal/${paal.id}`} color="link" size="sm">
                      {paal.id}
                    </Button>
                  </td>
                  <td>{paal.breedte}</td>
                  <td>{paal.diameter}</td>
                  <td>{paal.hoogte}</td>
                  <td>{paal.jaaronderhouduitgevoerd}</td>
                  <td>{paal.kwaliteitsniveauactueel}</td>
                  <td>{paal.kwaliteitsniveaugewenst}</td>
                  <td>{paal.lengte}</td>
                  <td>{paal.leverancier}</td>
                  <td>{paal.materiaal}</td>
                  <td>{paal.vorm}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/paal/${paal.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/paal/${paal.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/paal/${paal.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Paals found</div>
        )}
      </div>
    </div>
  );
};

export default Paal;
