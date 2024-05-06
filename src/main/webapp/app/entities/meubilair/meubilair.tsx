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

import { getEntities } from './meubilair.reducer';

export const Meubilair = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const meubilairList = useAppSelector(state => state.meubilair.entities);
  const loading = useAppSelector(state => state.meubilair.loading);

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
      <h2 id="meubilair-heading" data-cy="MeubilairHeading">
        Meubilairs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/meubilair/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Meubilair
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {meubilairList && meubilairList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanleghoogte')}>
                  Aanleghoogte <FontAwesomeIcon icon={getSortIconByFieldName('aanleghoogte')} />
                </th>
                <th className="hand" onClick={sort('bouwjaar')}>
                  Bouwjaar <FontAwesomeIcon icon={getSortIconByFieldName('bouwjaar')} />
                </th>
                <th className="hand" onClick={sort('breedte')}>
                  Breedte <FontAwesomeIcon icon={getSortIconByFieldName('breedte')} />
                </th>
                <th className="hand" onClick={sort('datumaanschaf')}>
                  Datumaanschaf <FontAwesomeIcon icon={getSortIconByFieldName('datumaanschaf')} />
                </th>
                <th className="hand" onClick={sort('diameter')}>
                  Diameter <FontAwesomeIcon icon={getSortIconByFieldName('diameter')} />
                </th>
                <th className="hand" onClick={sort('fabrikant')}>
                  Fabrikant <FontAwesomeIcon icon={getSortIconByFieldName('fabrikant')} />
                </th>
                <th className="hand" onClick={sort('gewicht')}>
                  Gewicht <FontAwesomeIcon icon={getSortIconByFieldName('gewicht')} />
                </th>
                <th className="hand" onClick={sort('hoogte')}>
                  Hoogte <FontAwesomeIcon icon={getSortIconByFieldName('hoogte')} />
                </th>
                <th className="hand" onClick={sort('installateur')}>
                  Installateur <FontAwesomeIcon icon={getSortIconByFieldName('installateur')} />
                </th>
                <th className="hand" onClick={sort('jaaronderhouduitgevoerd')}>
                  Jaaronderhouduitgevoerd <FontAwesomeIcon icon={getSortIconByFieldName('jaaronderhouduitgevoerd')} />
                </th>
                <th className="hand" onClick={sort('jaarpraktischeinde')}>
                  Jaarpraktischeinde <FontAwesomeIcon icon={getSortIconByFieldName('jaarpraktischeinde')} />
                </th>
                <th className="hand" onClick={sort('kleur')}>
                  Kleur <FontAwesomeIcon icon={getSortIconByFieldName('kleur')} />
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
                <th className="hand" onClick={sort('meubilairmateriaal')}>
                  Meubilairmateriaal <FontAwesomeIcon icon={getSortIconByFieldName('meubilairmateriaal')} />
                </th>
                <th className="hand" onClick={sort('model')}>
                  Model <FontAwesomeIcon icon={getSortIconByFieldName('model')} />
                </th>
                <th className="hand" onClick={sort('ondergrond')}>
                  Ondergrond <FontAwesomeIcon icon={getSortIconByFieldName('ondergrond')} />
                </th>
                <th className="hand" onClick={sort('oppervlakte')}>
                  Oppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('oppervlakte')} />
                </th>
                <th className="hand" onClick={sort('prijsaanschaf')}>
                  Prijsaanschaf <FontAwesomeIcon icon={getSortIconByFieldName('prijsaanschaf')} />
                </th>
                <th className="hand" onClick={sort('serienummer')}>
                  Serienummer <FontAwesomeIcon icon={getSortIconByFieldName('serienummer')} />
                </th>
                <th className="hand" onClick={sort('transponder')}>
                  Transponder <FontAwesomeIcon icon={getSortIconByFieldName('transponder')} />
                </th>
                <th className="hand" onClick={sort('transponderlocatie')}>
                  Transponderlocatie <FontAwesomeIcon icon={getSortIconByFieldName('transponderlocatie')} />
                </th>
                <th className="hand" onClick={sort('typefundering')}>
                  Typefundering <FontAwesomeIcon icon={getSortIconByFieldName('typefundering')} />
                </th>
                <th className="hand" onClick={sort('typeplaat')}>
                  Typeplaat <FontAwesomeIcon icon={getSortIconByFieldName('typeplaat')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {meubilairList.map((meubilair, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/meubilair/${meubilair.id}`} color="link" size="sm">
                      {meubilair.id}
                    </Button>
                  </td>
                  <td>{meubilair.aanleghoogte}</td>
                  <td>{meubilair.bouwjaar}</td>
                  <td>{meubilair.breedte}</td>
                  <td>
                    {meubilair.datumaanschaf ? (
                      <TextFormat type="date" value={meubilair.datumaanschaf} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{meubilair.diameter}</td>
                  <td>{meubilair.fabrikant}</td>
                  <td>{meubilair.gewicht}</td>
                  <td>{meubilair.hoogte}</td>
                  <td>{meubilair.installateur}</td>
                  <td>{meubilair.jaaronderhouduitgevoerd}</td>
                  <td>{meubilair.jaarpraktischeinde}</td>
                  <td>{meubilair.kleur}</td>
                  <td>{meubilair.kwaliteitsniveauactueel}</td>
                  <td>{meubilair.kwaliteitsniveaugewenst}</td>
                  <td>{meubilair.lengte}</td>
                  <td>{meubilair.leverancier}</td>
                  <td>{meubilair.meubilairmateriaal}</td>
                  <td>{meubilair.model}</td>
                  <td>{meubilair.ondergrond}</td>
                  <td>{meubilair.oppervlakte}</td>
                  <td>{meubilair.prijsaanschaf}</td>
                  <td>{meubilair.serienummer}</td>
                  <td>{meubilair.transponder}</td>
                  <td>{meubilair.transponderlocatie}</td>
                  <td>{meubilair.typefundering}</td>
                  <td>{meubilair.typeplaat ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/meubilair/${meubilair.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/meubilair/${meubilair.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/meubilair/${meubilair.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Meubilairs found</div>
        )}
      </div>
    </div>
  );
};

export default Meubilair;
