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

import { getEntities } from './verblijfsobject.reducer';

export const Verblijfsobject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verblijfsobjectList = useAppSelector(state => state.verblijfsobject.entities);
  const loading = useAppSelector(state => state.verblijfsobject.loading);

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
      <h2 id="verblijfsobject-heading" data-cy="VerblijfsobjectHeading">
        Verblijfsobjects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/verblijfsobject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verblijfsobject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verblijfsobjectList && verblijfsobjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantalkamers')}>
                  Aantalkamers <FontAwesomeIcon icon={getSortIconByFieldName('aantalkamers')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheid')}>
                  Datumbegingeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheid')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheid')}>
                  Datumeindegeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheid')} />
                </th>
                <th className="hand" onClick={sort('datumingang')}>
                  Datumingang <FontAwesomeIcon icon={getSortIconByFieldName('datumingang')} />
                </th>
                <th className="hand" onClick={sort('documentdatum')}>
                  Documentdatum <FontAwesomeIcon icon={getSortIconByFieldName('documentdatum')} />
                </th>
                <th className="hand" onClick={sort('documentnr')}>
                  Documentnr <FontAwesomeIcon icon={getSortIconByFieldName('documentnr')} />
                </th>
                <th className="hand" onClick={sort('gebruiksdoel')}>
                  Gebruiksdoel <FontAwesomeIcon icon={getSortIconByFieldName('gebruiksdoel')} />
                </th>
                <th className="hand" onClick={sort('geconstateerd')}>
                  Geconstateerd <FontAwesomeIcon icon={getSortIconByFieldName('geconstateerd')} />
                </th>
                <th className="hand" onClick={sort('geometrie')}>
                  Geometrie <FontAwesomeIcon icon={getSortIconByFieldName('geometrie')} />
                </th>
                <th className="hand" onClick={sort('hoogstebouwlaagverblijfsobject')}>
                  Hoogstebouwlaagverblijfsobject <FontAwesomeIcon icon={getSortIconByFieldName('hoogstebouwlaagverblijfsobject')} />
                </th>
                <th className="hand" onClick={sort('identificatie')}>
                  Identificatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatie')} />
                </th>
                <th className="hand" onClick={sort('inonderzoek')}>
                  Inonderzoek <FontAwesomeIcon icon={getSortIconByFieldName('inonderzoek')} />
                </th>
                <th className="hand" onClick={sort('laagstebouwlaagverblijfsobject')}>
                  Laagstebouwlaagverblijfsobject <FontAwesomeIcon icon={getSortIconByFieldName('laagstebouwlaagverblijfsobject')} />
                </th>
                <th className="hand" onClick={sort('ontsluitingverdieping')}>
                  Ontsluitingverdieping <FontAwesomeIcon icon={getSortIconByFieldName('ontsluitingverdieping')} />
                </th>
                <th className="hand" onClick={sort('soortwoonobject')}>
                  Soortwoonobject <FontAwesomeIcon icon={getSortIconByFieldName('soortwoonobject')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('toegangbouwlaagverblijfsobject')}>
                  Toegangbouwlaagverblijfsobject <FontAwesomeIcon icon={getSortIconByFieldName('toegangbouwlaagverblijfsobject')} />
                </th>
                <th className="hand" onClick={sort('versie')}>
                  Versie <FontAwesomeIcon icon={getSortIconByFieldName('versie')} />
                </th>
                <th>
                  Heeft Vastgoedobject <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Maaktdeeluitvan Pand <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verblijfsobjectList.map((verblijfsobject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verblijfsobject/${verblijfsobject.id}`} color="link" size="sm">
                      {verblijfsobject.id}
                    </Button>
                  </td>
                  <td>{verblijfsobject.aantalkamers}</td>
                  <td>
                    {verblijfsobject.datumbegingeldigheid ? (
                      <TextFormat type="date" value={verblijfsobject.datumbegingeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {verblijfsobject.datumeinde ? (
                      <TextFormat type="date" value={verblijfsobject.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {verblijfsobject.datumeindegeldigheid ? (
                      <TextFormat type="date" value={verblijfsobject.datumeindegeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {verblijfsobject.datumingang ? (
                      <TextFormat type="date" value={verblijfsobject.datumingang} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {verblijfsobject.documentdatum ? (
                      <TextFormat type="date" value={verblijfsobject.documentdatum} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{verblijfsobject.documentnr}</td>
                  <td>{verblijfsobject.gebruiksdoel}</td>
                  <td>{verblijfsobject.geconstateerd ? 'true' : 'false'}</td>
                  <td>{verblijfsobject.geometrie}</td>
                  <td>{verblijfsobject.hoogstebouwlaagverblijfsobject}</td>
                  <td>{verblijfsobject.identificatie}</td>
                  <td>{verblijfsobject.inonderzoek ? 'true' : 'false'}</td>
                  <td>{verblijfsobject.laagstebouwlaagverblijfsobject}</td>
                  <td>{verblijfsobject.ontsluitingverdieping}</td>
                  <td>{verblijfsobject.soortwoonobject}</td>
                  <td>{verblijfsobject.status}</td>
                  <td>{verblijfsobject.toegangbouwlaagverblijfsobject}</td>
                  <td>{verblijfsobject.versie}</td>
                  <td>
                    {verblijfsobject.heeftVastgoedobject ? (
                      <Link to={`/vastgoedobject/${verblijfsobject.heeftVastgoedobject.id}`}>{verblijfsobject.heeftVastgoedobject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {verblijfsobject.maaktdeeluitvanPands
                      ? verblijfsobject.maaktdeeluitvanPands.map((val, j) => (
                          <span key={j}>
                            <Link to={`/pand/${val.id}`}>{val.id}</Link>
                            {j === verblijfsobject.maaktdeeluitvanPands.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/verblijfsobject/${verblijfsobject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/verblijfsobject/${verblijfsobject.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verblijfsobject/${verblijfsobject.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verblijfsobjects found</div>
        )}
      </div>
    </div>
  );
};

export default Verblijfsobject;
