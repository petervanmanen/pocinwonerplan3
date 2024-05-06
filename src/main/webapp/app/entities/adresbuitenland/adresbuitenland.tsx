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

import { getEntities } from './adresbuitenland.reducer';

export const Adresbuitenland = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const adresbuitenlandList = useAppSelector(state => state.adresbuitenland.entities);
  const loading = useAppSelector(state => state.adresbuitenland.loading);

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
      <h2 id="adresbuitenland-heading" data-cy="AdresbuitenlandHeading">
        Adresbuitenlands
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/adresbuitenland/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Adresbuitenland
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {adresbuitenlandList && adresbuitenlandList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adresregelbuitenland1')}>
                  Adresregelbuitenland 1 <FontAwesomeIcon icon={getSortIconByFieldName('adresregelbuitenland1')} />
                </th>
                <th className="hand" onClick={sort('adresregelbuitenland2')}>
                  Adresregelbuitenland 2 <FontAwesomeIcon icon={getSortIconByFieldName('adresregelbuitenland2')} />
                </th>
                <th className="hand" onClick={sort('adresregelbuitenland3')}>
                  Adresregelbuitenland 3 <FontAwesomeIcon icon={getSortIconByFieldName('adresregelbuitenland3')} />
                </th>
                <th className="hand" onClick={sort('datumaanvangadresbuitenland')}>
                  Datumaanvangadresbuitenland <FontAwesomeIcon icon={getSortIconByFieldName('datumaanvangadresbuitenland')} />
                </th>
                <th className="hand" onClick={sort('datuminschrijvinggemeente')}>
                  Datuminschrijvinggemeente <FontAwesomeIcon icon={getSortIconByFieldName('datuminschrijvinggemeente')} />
                </th>
                <th className="hand" onClick={sort('datumvestigingnederland')}>
                  Datumvestigingnederland <FontAwesomeIcon icon={getSortIconByFieldName('datumvestigingnederland')} />
                </th>
                <th className="hand" onClick={sort('gemeentevaninschrijving')}>
                  Gemeentevaninschrijving <FontAwesomeIcon icon={getSortIconByFieldName('gemeentevaninschrijving')} />
                </th>
                <th className="hand" onClick={sort('landadresbuitenland')}>
                  Landadresbuitenland <FontAwesomeIcon icon={getSortIconByFieldName('landadresbuitenland')} />
                </th>
                <th className="hand" onClick={sort('landwaarvandaaningeschreven')}>
                  Landwaarvandaaningeschreven <FontAwesomeIcon icon={getSortIconByFieldName('landwaarvandaaningeschreven')} />
                </th>
                <th className="hand" onClick={sort('omschrijvingvandeaangifteadreshouding')}>
                  Omschrijvingvandeaangifteadreshouding{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('omschrijvingvandeaangifteadreshouding')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {adresbuitenlandList.map((adresbuitenland, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/adresbuitenland/${adresbuitenland.id}`} color="link" size="sm">
                      {adresbuitenland.id}
                    </Button>
                  </td>
                  <td>{adresbuitenland.adresregelbuitenland1}</td>
                  <td>{adresbuitenland.adresregelbuitenland2}</td>
                  <td>{adresbuitenland.adresregelbuitenland3}</td>
                  <td>
                    {adresbuitenland.datumaanvangadresbuitenland ? (
                      <TextFormat type="date" value={adresbuitenland.datumaanvangadresbuitenland} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {adresbuitenland.datuminschrijvinggemeente ? (
                      <TextFormat type="date" value={adresbuitenland.datuminschrijvinggemeente} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {adresbuitenland.datumvestigingnederland ? (
                      <TextFormat type="date" value={adresbuitenland.datumvestigingnederland} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{adresbuitenland.gemeentevaninschrijving}</td>
                  <td>{adresbuitenland.landadresbuitenland}</td>
                  <td>{adresbuitenland.landwaarvandaaningeschreven}</td>
                  <td>{adresbuitenland.omschrijvingvandeaangifteadreshouding}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/adresbuitenland/${adresbuitenland.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/adresbuitenland/${adresbuitenland.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/adresbuitenland/${adresbuitenland.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Adresbuitenlands found</div>
        )}
      </div>
    </div>
  );
};

export default Adresbuitenland;
