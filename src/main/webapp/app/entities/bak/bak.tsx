import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './bak.reducer';

export const Bak = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const bakList = useAppSelector(state => state.bak.entities);
  const loading = useAppSelector(state => state.bak.loading);

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
      <h2 id="bak-heading" data-cy="BakHeading">
        Baks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/bak/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Bak
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bakList && bakList.length > 0 ? (
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
                <th className="hand" onClick={sort('gewichtleeg')}>
                  Gewichtleeg <FontAwesomeIcon icon={getSortIconByFieldName('gewichtleeg')} />
                </th>
                <th className="hand" onClick={sort('gewichtvol')}>
                  Gewichtvol <FontAwesomeIcon icon={getSortIconByFieldName('gewichtvol')} />
                </th>
                <th className="hand" onClick={sort('hoogte')}>
                  Hoogte <FontAwesomeIcon icon={getSortIconByFieldName('hoogte')} />
                </th>
                <th className="hand" onClick={sort('inhoud')}>
                  Inhoud <FontAwesomeIcon icon={getSortIconByFieldName('inhoud')} />
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
                <th className="hand" onClick={sort('materiaal')}>
                  Materiaal <FontAwesomeIcon icon={getSortIconByFieldName('materiaal')} />
                </th>
                <th className="hand" onClick={sort('verplaatsbaar')}>
                  Verplaatsbaar <FontAwesomeIcon icon={getSortIconByFieldName('verplaatsbaar')} />
                </th>
                <th className="hand" onClick={sort('vorm')}>
                  Vorm <FontAwesomeIcon icon={getSortIconByFieldName('vorm')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bakList.map((bak, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bak/${bak.id}`} color="link" size="sm">
                      {bak.id}
                    </Button>
                  </td>
                  <td>{bak.breedte}</td>
                  <td>{bak.diameter}</td>
                  <td>{bak.gewichtleeg}</td>
                  <td>{bak.gewichtvol}</td>
                  <td>{bak.hoogte}</td>
                  <td>{bak.inhoud}</td>
                  <td>{bak.jaaronderhouduitgevoerd}</td>
                  <td>{bak.kwaliteitsniveauactueel}</td>
                  <td>{bak.kwaliteitsniveaugewenst}</td>
                  <td>{bak.lengte}</td>
                  <td>{bak.materiaal}</td>
                  <td>{bak.verplaatsbaar ? 'true' : 'false'}</td>
                  <td>{bak.vorm}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/bak/${bak.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/bak/${bak.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/bak/${bak.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Baks found</div>
        )}
      </div>
    </div>
  );
};

export default Bak;
