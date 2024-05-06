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

import { getEntities } from './openbareruimte.reducer';

export const Openbareruimte = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const openbareruimteList = useAppSelector(state => state.openbareruimte.entities);
  const loading = useAppSelector(state => state.openbareruimte.loading);

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
      <h2 id="openbareruimte-heading" data-cy="OpenbareruimteHeading">
        Openbareruimtes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/openbareruimte/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Openbareruimte
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {openbareruimteList && openbareruimteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
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
                <th className="hand" onClick={sort('geconstateerd')}>
                  Geconstateerd <FontAwesomeIcon icon={getSortIconByFieldName('geconstateerd')} />
                </th>
                <th className="hand" onClick={sort('geometrie')}>
                  Geometrie <FontAwesomeIcon icon={getSortIconByFieldName('geometrie')} />
                </th>
                <th className="hand" onClick={sort('huisnummerrangeevenenonevennummers')}>
                  Huisnummerrangeevenenonevennummers <FontAwesomeIcon icon={getSortIconByFieldName('huisnummerrangeevenenonevennummers')} />
                </th>
                <th className="hand" onClick={sort('huisnummerrangeevennummers')}>
                  Huisnummerrangeevennummers <FontAwesomeIcon icon={getSortIconByFieldName('huisnummerrangeevennummers')} />
                </th>
                <th className="hand" onClick={sort('huisnummerrangeonevennummers')}>
                  Huisnummerrangeonevennummers <FontAwesomeIcon icon={getSortIconByFieldName('huisnummerrangeonevennummers')} />
                </th>
                <th className="hand" onClick={sort('identificatie')}>
                  Identificatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatie')} />
                </th>
                <th className="hand" onClick={sort('inonderzoek')}>
                  Inonderzoek <FontAwesomeIcon icon={getSortIconByFieldName('inonderzoek')} />
                </th>
                <th className="hand" onClick={sort('labelnaam')}>
                  Labelnaam <FontAwesomeIcon icon={getSortIconByFieldName('labelnaam')} />
                </th>
                <th className="hand" onClick={sort('naamopenbareruimte')}>
                  Naamopenbareruimte <FontAwesomeIcon icon={getSortIconByFieldName('naamopenbareruimte')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('straatcode')}>
                  Straatcode <FontAwesomeIcon icon={getSortIconByFieldName('straatcode')} />
                </th>
                <th className="hand" onClick={sort('straatnaam')}>
                  Straatnaam <FontAwesomeIcon icon={getSortIconByFieldName('straatnaam')} />
                </th>
                <th className="hand" onClick={sort('typeopenbareruimte')}>
                  Typeopenbareruimte <FontAwesomeIcon icon={getSortIconByFieldName('typeopenbareruimte')} />
                </th>
                <th className="hand" onClick={sort('versie')}>
                  Versie <FontAwesomeIcon icon={getSortIconByFieldName('versie')} />
                </th>
                <th className="hand" onClick={sort('wegsegment')}>
                  Wegsegment <FontAwesomeIcon icon={getSortIconByFieldName('wegsegment')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {openbareruimteList.map((openbareruimte, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/openbareruimte/${openbareruimte.id}`} color="link" size="sm">
                      {openbareruimte.id}
                    </Button>
                  </td>
                  <td>
                    {openbareruimte.datumbegingeldigheid ? (
                      <TextFormat type="date" value={openbareruimte.datumbegingeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {openbareruimte.datumeinde ? (
                      <TextFormat type="date" value={openbareruimte.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {openbareruimte.datumeindegeldigheid ? (
                      <TextFormat type="date" value={openbareruimte.datumeindegeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {openbareruimte.datumingang ? (
                      <TextFormat type="date" value={openbareruimte.datumingang} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{openbareruimte.geconstateerd ? 'true' : 'false'}</td>
                  <td>{openbareruimte.geometrie}</td>
                  <td>{openbareruimte.huisnummerrangeevenenonevennummers}</td>
                  <td>{openbareruimte.huisnummerrangeevennummers}</td>
                  <td>{openbareruimte.huisnummerrangeonevennummers}</td>
                  <td>{openbareruimte.identificatie}</td>
                  <td>{openbareruimte.inonderzoek ? 'true' : 'false'}</td>
                  <td>{openbareruimte.labelnaam}</td>
                  <td>{openbareruimte.naamopenbareruimte}</td>
                  <td>{openbareruimte.status}</td>
                  <td>{openbareruimte.straatcode}</td>
                  <td>{openbareruimte.straatnaam}</td>
                  <td>{openbareruimte.typeopenbareruimte}</td>
                  <td>{openbareruimte.versie}</td>
                  <td>{openbareruimte.wegsegment}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/openbareruimte/${openbareruimte.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/openbareruimte/${openbareruimte.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/openbareruimte/${openbareruimte.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Openbareruimtes found</div>
        )}
      </div>
    </div>
  );
};

export default Openbareruimte;
