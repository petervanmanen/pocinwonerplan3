import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './leiding.reducer';

export const Leiding = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const leidingList = useAppSelector(state => state.leiding.entities);
  const loading = useAppSelector(state => state.leiding.loading);

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
      <h2 id="leiding-heading" data-cy="LeidingHeading">
        Leidings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/leiding/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Leiding
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {leidingList && leidingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('afwijkendedieptelegging')}>
                  Afwijkendedieptelegging <FontAwesomeIcon icon={getSortIconByFieldName('afwijkendedieptelegging')} />
                </th>
                <th className="hand" onClick={sort('breedte')}>
                  Breedte <FontAwesomeIcon icon={getSortIconByFieldName('breedte')} />
                </th>
                <th className="hand" onClick={sort('diameter')}>
                  Diameter <FontAwesomeIcon icon={getSortIconByFieldName('diameter')} />
                </th>
                <th className="hand" onClick={sort('diepte')}>
                  Diepte <FontAwesomeIcon icon={getSortIconByFieldName('diepte')} />
                </th>
                <th className="hand" onClick={sort('eisvoorzorgsmaatregel')}>
                  Eisvoorzorgsmaatregel <FontAwesomeIcon icon={getSortIconByFieldName('eisvoorzorgsmaatregel')} />
                </th>
                <th className="hand" onClick={sort('geonauwkeurigheidxy')}>
                  Geonauwkeurigheidxy <FontAwesomeIcon icon={getSortIconByFieldName('geonauwkeurigheidxy')} />
                </th>
                <th className="hand" onClick={sort('hoogte')}>
                  Hoogte <FontAwesomeIcon icon={getSortIconByFieldName('hoogte')} />
                </th>
                <th className="hand" onClick={sort('jaaronderhouduitgevoerd')}>
                  Jaaronderhouduitgevoerd <FontAwesomeIcon icon={getSortIconByFieldName('jaaronderhouduitgevoerd')} />
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
                <th className="hand" onClick={sort('themaimkl')}>
                  Themaimkl <FontAwesomeIcon icon={getSortIconByFieldName('themaimkl')} />
                </th>
                <th className="hand" onClick={sort('verhoogdrisico')}>
                  Verhoogdrisico <FontAwesomeIcon icon={getSortIconByFieldName('verhoogdrisico')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leidingList.map((leiding, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/leiding/${leiding.id}`} color="link" size="sm">
                      {leiding.id}
                    </Button>
                  </td>
                  <td>{leiding.afwijkendedieptelegging}</td>
                  <td>{leiding.breedte}</td>
                  <td>{leiding.diameter}</td>
                  <td>{leiding.diepte}</td>
                  <td>{leiding.eisvoorzorgsmaatregel}</td>
                  <td>{leiding.geonauwkeurigheidxy}</td>
                  <td>{leiding.hoogte}</td>
                  <td>{leiding.jaaronderhouduitgevoerd}</td>
                  <td>{leiding.lengte}</td>
                  <td>{leiding.leverancier}</td>
                  <td>{leiding.materiaal}</td>
                  <td>{leiding.themaimkl}</td>
                  <td>{leiding.verhoogdrisico}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/leiding/${leiding.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/leiding/${leiding.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/leiding/${leiding.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Leidings found</div>
        )}
      </div>
    </div>
  );
};

export default Leiding;
