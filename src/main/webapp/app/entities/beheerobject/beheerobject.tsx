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

import { getEntities } from './beheerobject.reducer';

export const Beheerobject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const beheerobjectList = useAppSelector(state => state.beheerobject.entities);
  const loading = useAppSelector(state => state.beheerobject.loading);

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
      <h2 id="beheerobject-heading" data-cy="BeheerobjectHeading">
        Beheerobjects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/beheerobject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Beheerobject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {beheerobjectList && beheerobjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aangemaaktdoor')}>
                  Aangemaaktdoor <FontAwesomeIcon icon={getSortIconByFieldName('aangemaaktdoor')} />
                </th>
                <th className="hand" onClick={sort('begingarantieperiode')}>
                  Begingarantieperiode <FontAwesomeIcon icon={getSortIconByFieldName('begingarantieperiode')} />
                </th>
                <th className="hand" onClick={sort('beheergebied')}>
                  Beheergebied <FontAwesomeIcon icon={getSortIconByFieldName('beheergebied')} />
                </th>
                <th className="hand" onClick={sort('beheerobjectbeheervak')}>
                  Beheerobjectbeheervak <FontAwesomeIcon icon={getSortIconByFieldName('beheerobjectbeheervak')} />
                </th>
                <th className="hand" onClick={sort('beheerobjectgebruiksfunctie')}>
                  Beheerobjectgebruiksfunctie <FontAwesomeIcon icon={getSortIconByFieldName('beheerobjectgebruiksfunctie')} />
                </th>
                <th className="hand" onClick={sort('beheerobjectmemo')}>
                  Beheerobjectmemo <FontAwesomeIcon icon={getSortIconByFieldName('beheerobjectmemo')} />
                </th>
                <th className="hand" onClick={sort('beschermdefloraenfauna')}>
                  Beschermdefloraenfauna <FontAwesomeIcon icon={getSortIconByFieldName('beschermdefloraenfauna')} />
                </th>
                <th className="hand" onClick={sort('buurt')}>
                  Buurt <FontAwesomeIcon icon={getSortIconByFieldName('buurt')} />
                </th>
                <th className="hand" onClick={sort('conversieid')}>
                  Conversieid <FontAwesomeIcon icon={getSortIconByFieldName('conversieid')} />
                </th>
                <th className="hand" onClick={sort('datummutatie')}>
                  Datummutatie <FontAwesomeIcon icon={getSortIconByFieldName('datummutatie')} />
                </th>
                <th className="hand" onClick={sort('datumoplevering')}>
                  Datumoplevering <FontAwesomeIcon icon={getSortIconByFieldName('datumoplevering')} />
                </th>
                <th className="hand" onClick={sort('datumpublicatielv')}>
                  Datumpublicatielv <FontAwesomeIcon icon={getSortIconByFieldName('datumpublicatielv')} />
                </th>
                <th className="hand" onClick={sort('datumverwijdering')}>
                  Datumverwijdering <FontAwesomeIcon icon={getSortIconByFieldName('datumverwijdering')} />
                </th>
                <th className="hand" onClick={sort('eindegarantieperiode')}>
                  Eindegarantieperiode <FontAwesomeIcon icon={getSortIconByFieldName('eindegarantieperiode')} />
                </th>
                <th className="hand" onClick={sort('gebiedstype')}>
                  Gebiedstype <FontAwesomeIcon icon={getSortIconByFieldName('gebiedstype')} />
                </th>
                <th className="hand" onClick={sort('gemeente')}>
                  Gemeente <FontAwesomeIcon icon={getSortIconByFieldName('gemeente')} />
                </th>
                <th className="hand" onClick={sort('geometrie')}>
                  Geometrie <FontAwesomeIcon icon={getSortIconByFieldName('geometrie')} />
                </th>
                <th className="hand" onClick={sort('gewijzigddoor')}>
                  Gewijzigddoor <FontAwesomeIcon icon={getSortIconByFieldName('gewijzigddoor')} />
                </th>
                <th className="hand" onClick={sort('grondsoort')}>
                  Grondsoort <FontAwesomeIcon icon={getSortIconByFieldName('grondsoort')} />
                </th>
                <th className="hand" onClick={sort('grondsoortplus')}>
                  Grondsoortplus <FontAwesomeIcon icon={getSortIconByFieldName('grondsoortplus')} />
                </th>
                <th className="hand" onClick={sort('identificatieimbor')}>
                  Identificatieimbor <FontAwesomeIcon icon={getSortIconByFieldName('identificatieimbor')} />
                </th>
                <th className="hand" onClick={sort('identificatieimgeo')}>
                  Identificatieimgeo <FontAwesomeIcon icon={getSortIconByFieldName('identificatieimgeo')} />
                </th>
                <th className="hand" onClick={sort('jaarvanaanleg')}>
                  Jaarvanaanleg <FontAwesomeIcon icon={getSortIconByFieldName('jaarvanaanleg')} />
                </th>
                <th className="hand" onClick={sort('eobjectbegintijd')}>
                  Eobjectbegintijd <FontAwesomeIcon icon={getSortIconByFieldName('eobjectbegintijd')} />
                </th>
                <th className="hand" onClick={sort('eobjecteindtijd')}>
                  Eobjecteindtijd <FontAwesomeIcon icon={getSortIconByFieldName('eobjecteindtijd')} />
                </th>
                <th className="hand" onClick={sort('onderhoudsplichtige')}>
                  Onderhoudsplichtige <FontAwesomeIcon icon={getSortIconByFieldName('onderhoudsplichtige')} />
                </th>
                <th className="hand" onClick={sort('openbareruimte')}>
                  Openbareruimte <FontAwesomeIcon icon={getSortIconByFieldName('openbareruimte')} />
                </th>
                <th className="hand" onClick={sort('postcode')}>
                  Postcode <FontAwesomeIcon icon={getSortIconByFieldName('postcode')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteligging')}>
                  Relatievehoogteligging <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteligging')} />
                </th>
                <th className="hand" onClick={sort('stadsdeel')}>
                  Stadsdeel <FontAwesomeIcon icon={getSortIconByFieldName('stadsdeel')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('theoretischeindejaar')}>
                  Theoretischeindejaar <FontAwesomeIcon icon={getSortIconByFieldName('theoretischeindejaar')} />
                </th>
                <th className="hand" onClick={sort('tijdstipregistratie')}>
                  Tijdstipregistratie <FontAwesomeIcon icon={getSortIconByFieldName('tijdstipregistratie')} />
                </th>
                <th className="hand" onClick={sort('typebeheerder')}>
                  Typebeheerder <FontAwesomeIcon icon={getSortIconByFieldName('typebeheerder')} />
                </th>
                <th className="hand" onClick={sort('typebeheerderplus')}>
                  Typebeheerderplus <FontAwesomeIcon icon={getSortIconByFieldName('typebeheerderplus')} />
                </th>
                <th className="hand" onClick={sort('typeeigenaar')}>
                  Typeeigenaar <FontAwesomeIcon icon={getSortIconByFieldName('typeeigenaar')} />
                </th>
                <th className="hand" onClick={sort('typeeigenaarplus')}>
                  Typeeigenaarplus <FontAwesomeIcon icon={getSortIconByFieldName('typeeigenaarplus')} />
                </th>
                <th className="hand" onClick={sort('typeligging')}>
                  Typeligging <FontAwesomeIcon icon={getSortIconByFieldName('typeligging')} />
                </th>
                <th className="hand" onClick={sort('waterschap')}>
                  Waterschap <FontAwesomeIcon icon={getSortIconByFieldName('waterschap')} />
                </th>
                <th className="hand" onClick={sort('wijk')}>
                  Wijk <FontAwesomeIcon icon={getSortIconByFieldName('wijk')} />
                </th>
                <th className="hand" onClick={sort('woonplaats')}>
                  Woonplaats <FontAwesomeIcon icon={getSortIconByFieldName('woonplaats')} />
                </th>
                <th className="hand" onClick={sort('zettingsgevoeligheid')}>
                  Zettingsgevoeligheid <FontAwesomeIcon icon={getSortIconByFieldName('zettingsgevoeligheid')} />
                </th>
                <th className="hand" onClick={sort('zettingsgevoeligheidplus')}>
                  Zettingsgevoeligheidplus <FontAwesomeIcon icon={getSortIconByFieldName('zettingsgevoeligheidplus')} />
                </th>
                <th>
                  Betreft Melding <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {beheerobjectList.map((beheerobject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/beheerobject/${beheerobject.id}`} color="link" size="sm">
                      {beheerobject.id}
                    </Button>
                  </td>
                  <td>{beheerobject.aangemaaktdoor}</td>
                  <td>
                    {beheerobject.begingarantieperiode ? (
                      <TextFormat type="date" value={beheerobject.begingarantieperiode} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{beheerobject.beheergebied}</td>
                  <td>{beheerobject.beheerobjectbeheervak}</td>
                  <td>{beheerobject.beheerobjectgebruiksfunctie}</td>
                  <td>{beheerobject.beheerobjectmemo}</td>
                  <td>{beheerobject.beschermdefloraenfauna ? 'true' : 'false'}</td>
                  <td>{beheerobject.buurt}</td>
                  <td>{beheerobject.conversieid}</td>
                  <td>
                    {beheerobject.datummutatie ? (
                      <TextFormat type="date" value={beheerobject.datummutatie} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {beheerobject.datumoplevering ? (
                      <TextFormat type="date" value={beheerobject.datumoplevering} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {beheerobject.datumpublicatielv ? (
                      <TextFormat type="date" value={beheerobject.datumpublicatielv} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {beheerobject.datumverwijdering ? (
                      <TextFormat type="date" value={beheerobject.datumverwijdering} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {beheerobject.eindegarantieperiode ? (
                      <TextFormat type="date" value={beheerobject.eindegarantieperiode} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{beheerobject.gebiedstype}</td>
                  <td>{beheerobject.gemeente}</td>
                  <td>{beheerobject.geometrie}</td>
                  <td>{beheerobject.gewijzigddoor}</td>
                  <td>{beheerobject.grondsoort}</td>
                  <td>{beheerobject.grondsoortplus}</td>
                  <td>{beheerobject.identificatieimbor}</td>
                  <td>{beheerobject.identificatieimgeo}</td>
                  <td>{beheerobject.jaarvanaanleg}</td>
                  <td>{beheerobject.eobjectbegintijd}</td>
                  <td>{beheerobject.eobjecteindtijd}</td>
                  <td>{beheerobject.onderhoudsplichtige}</td>
                  <td>{beheerobject.openbareruimte}</td>
                  <td>{beheerobject.postcode}</td>
                  <td>{beheerobject.relatievehoogteligging}</td>
                  <td>{beheerobject.stadsdeel}</td>
                  <td>{beheerobject.status}</td>
                  <td>{beheerobject.theoretischeindejaar}</td>
                  <td>{beheerobject.tijdstipregistratie}</td>
                  <td>{beheerobject.typebeheerder}</td>
                  <td>{beheerobject.typebeheerderplus}</td>
                  <td>{beheerobject.typeeigenaar}</td>
                  <td>{beheerobject.typeeigenaarplus}</td>
                  <td>{beheerobject.typeligging}</td>
                  <td>{beheerobject.waterschap}</td>
                  <td>{beheerobject.wijk}</td>
                  <td>{beheerobject.woonplaats}</td>
                  <td>{beheerobject.zettingsgevoeligheid}</td>
                  <td>{beheerobject.zettingsgevoeligheidplus}</td>
                  <td>
                    {beheerobject.betreftMeldings
                      ? beheerobject.betreftMeldings.map((val, j) => (
                          <span key={j}>
                            <Link to={`/melding/${val.id}`}>{val.id}</Link>
                            {j === beheerobject.betreftMeldings.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/beheerobject/${beheerobject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/beheerobject/${beheerobject.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/beheerobject/${beheerobject.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Beheerobjects found</div>
        )}
      </div>
    </div>
  );
};

export default Beheerobject;
