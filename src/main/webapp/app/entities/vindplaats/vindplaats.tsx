import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vindplaats.reducer';

export const Vindplaats = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vindplaatsList = useAppSelector(state => state.vindplaats.entities);
  const loading = useAppSelector(state => state.vindplaats.loading);

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
      <h2 id="vindplaats-heading" data-cy="VindplaatsHeading">
        Vindplaats
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vindplaats/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vindplaats
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vindplaatsList && vindplaatsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aard')}>
                  Aard <FontAwesomeIcon icon={getSortIconByFieldName('aard')} />
                </th>
                <th className="hand" onClick={sort('begindatering')}>
                  Begindatering <FontAwesomeIcon icon={getSortIconByFieldName('begindatering')} />
                </th>
                <th className="hand" onClick={sort('beschrijving')}>
                  Beschrijving <FontAwesomeIcon icon={getSortIconByFieldName('beschrijving')} />
                </th>
                <th className="hand" onClick={sort('bibliografie')}>
                  Bibliografie <FontAwesomeIcon icon={getSortIconByFieldName('bibliografie')} />
                </th>
                <th className="hand" onClick={sort('datering')}>
                  Datering <FontAwesomeIcon icon={getSortIconByFieldName('datering')} />
                </th>
                <th className="hand" onClick={sort('depot')}>
                  Depot <FontAwesomeIcon icon={getSortIconByFieldName('depot')} />
                </th>
                <th className="hand" onClick={sort('documentatie')}>
                  Documentatie <FontAwesomeIcon icon={getSortIconByFieldName('documentatie')} />
                </th>
                <th className="hand" onClick={sort('einddatering')}>
                  Einddatering <FontAwesomeIcon icon={getSortIconByFieldName('einddatering')} />
                </th>
                <th className="hand" onClick={sort('gemeente')}>
                  Gemeente <FontAwesomeIcon icon={getSortIconByFieldName('gemeente')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('mobilia')}>
                  Mobilia <FontAwesomeIcon icon={getSortIconByFieldName('mobilia')} />
                </th>
                <th className="hand" onClick={sort('onderzoek')}>
                  Onderzoek <FontAwesomeIcon icon={getSortIconByFieldName('onderzoek')} />
                </th>
                <th className="hand" onClick={sort('projectcode')}>
                  Projectcode <FontAwesomeIcon icon={getSortIconByFieldName('projectcode')} />
                </th>
                <th className="hand" onClick={sort('vindplaats')}>
                  Vindplaats <FontAwesomeIcon icon={getSortIconByFieldName('vindplaats')} />
                </th>
                <th>
                  Hoortbij Project <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Istevindenin Depot <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Istevindenin Kast <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Istevindenin Plank <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Istevindenin Stelling <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vindplaatsList.map((vindplaats, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vindplaats/${vindplaats.id}`} color="link" size="sm">
                      {vindplaats.id}
                    </Button>
                  </td>
                  <td>{vindplaats.aard}</td>
                  <td>{vindplaats.begindatering}</td>
                  <td>{vindplaats.beschrijving}</td>
                  <td>{vindplaats.bibliografie}</td>
                  <td>{vindplaats.datering}</td>
                  <td>{vindplaats.depot}</td>
                  <td>{vindplaats.documentatie}</td>
                  <td>{vindplaats.einddatering}</td>
                  <td>{vindplaats.gemeente}</td>
                  <td>{vindplaats.locatie}</td>
                  <td>{vindplaats.mobilia}</td>
                  <td>{vindplaats.onderzoek}</td>
                  <td>{vindplaats.projectcode}</td>
                  <td>{vindplaats.vindplaats}</td>
                  <td>
                    {vindplaats.hoortbijProject ? (
                      <Link to={`/project/${vindplaats.hoortbijProject.id}`}>{vindplaats.hoortbijProject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vindplaats.istevindeninDepot ? (
                      <Link to={`/depot/${vindplaats.istevindeninDepot.id}`}>{vindplaats.istevindeninDepot.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vindplaats.istevindeninKast ? (
                      <Link to={`/kast/${vindplaats.istevindeninKast.id}`}>{vindplaats.istevindeninKast.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vindplaats.istevindeninPlank ? (
                      <Link to={`/plank/${vindplaats.istevindeninPlank.id}`}>{vindplaats.istevindeninPlank.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vindplaats.istevindeninStelling ? (
                      <Link to={`/stelling/${vindplaats.istevindeninStelling.id}`}>{vindplaats.istevindeninStelling.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vindplaats/${vindplaats.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vindplaats/${vindplaats.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vindplaats/${vindplaats.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vindplaats found</div>
        )}
      </div>
    </div>
  );
};

export default Vindplaats;
