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

import { getEntities } from './pand.reducer';

export const Pand = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const pandList = useAppSelector(state => state.pand.entities);
  const loading = useAppSelector(state => state.pand.loading);

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
      <h2 id="pand-heading" data-cy="PandHeading">
        Pands
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/pand/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Pand
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pandList && pandList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('brutoinhoudpand')}>
                  Brutoinhoudpand <FontAwesomeIcon icon={getSortIconByFieldName('brutoinhoudpand')} />
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
                <th className="hand" onClick={sort('geometriebovenaanzicht')}>
                  Geometriebovenaanzicht <FontAwesomeIcon icon={getSortIconByFieldName('geometriebovenaanzicht')} />
                </th>
                <th className="hand" onClick={sort('geometriemaaiveld')}>
                  Geometriemaaiveld <FontAwesomeIcon icon={getSortIconByFieldName('geometriemaaiveld')} />
                </th>
                <th className="hand" onClick={sort('geometriepunt')}>
                  Geometriepunt <FontAwesomeIcon icon={getSortIconByFieldName('geometriepunt')} />
                </th>
                <th className="hand" onClick={sort('hoogstebouwlaagpand')}>
                  Hoogstebouwlaagpand <FontAwesomeIcon icon={getSortIconByFieldName('hoogstebouwlaagpand')} />
                </th>
                <th className="hand" onClick={sort('identificatie')}>
                  Identificatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatie')} />
                </th>
                <th className="hand" onClick={sort('inonderzoek')}>
                  Inonderzoek <FontAwesomeIcon icon={getSortIconByFieldName('inonderzoek')} />
                </th>
                <th className="hand" onClick={sort('laagstebouwlaagpand')}>
                  Laagstebouwlaagpand <FontAwesomeIcon icon={getSortIconByFieldName('laagstebouwlaagpand')} />
                </th>
                <th className="hand" onClick={sort('oorspronkelijkbouwjaar')}>
                  Oorspronkelijkbouwjaar <FontAwesomeIcon icon={getSortIconByFieldName('oorspronkelijkbouwjaar')} />
                </th>
                <th className="hand" onClick={sort('oppervlakte')}>
                  Oppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('oppervlakte')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteliggingpand')}>
                  Relatievehoogteliggingpand <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteliggingpand')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('statusvoortgangbouw')}>
                  Statusvoortgangbouw <FontAwesomeIcon icon={getSortIconByFieldName('statusvoortgangbouw')} />
                </th>
                <th className="hand" onClick={sort('versie')}>
                  Versie <FontAwesomeIcon icon={getSortIconByFieldName('versie')} />
                </th>
                <th>
                  Heeft Vastgoedobject <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Zonderverblijfsobjectligtin Buurt <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Maaktdeeluitvan Verblijfsobject <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pandList.map((pand, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pand/${pand.id}`} color="link" size="sm">
                      {pand.id}
                    </Button>
                  </td>
                  <td>{pand.brutoinhoudpand}</td>
                  <td>
                    {pand.datumbegingeldigheid ? (
                      <TextFormat type="date" value={pand.datumbegingeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{pand.datumeinde ? <TextFormat type="date" value={pand.datumeinde} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>
                    {pand.datumeindegeldigheid ? (
                      <TextFormat type="date" value={pand.datumeindegeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{pand.datumingang ? <TextFormat type="date" value={pand.datumingang} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{pand.geconstateerd ? 'true' : 'false'}</td>
                  <td>{pand.geometriebovenaanzicht}</td>
                  <td>{pand.geometriemaaiveld}</td>
                  <td>{pand.geometriepunt}</td>
                  <td>{pand.hoogstebouwlaagpand}</td>
                  <td>{pand.identificatie}</td>
                  <td>{pand.inonderzoek ? 'true' : 'false'}</td>
                  <td>{pand.laagstebouwlaagpand}</td>
                  <td>{pand.oorspronkelijkbouwjaar}</td>
                  <td>{pand.oppervlakte}</td>
                  <td>{pand.relatievehoogteliggingpand}</td>
                  <td>{pand.status}</td>
                  <td>{pand.statusvoortgangbouw}</td>
                  <td>{pand.versie}</td>
                  <td>
                    {pand.heeftVastgoedobject ? (
                      <Link to={`/vastgoedobject/${pand.heeftVastgoedobject.id}`}>{pand.heeftVastgoedobject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {pand.zonderverblijfsobjectligtinBuurt ? (
                      <Link to={`/buurt/${pand.zonderverblijfsobjectligtinBuurt.id}`}>{pand.zonderverblijfsobjectligtinBuurt.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {pand.maaktdeeluitvanVerblijfsobjects
                      ? pand.maaktdeeluitvanVerblijfsobjects.map((val, j) => (
                          <span key={j}>
                            <Link to={`/verblijfsobject/${val.id}`}>{val.id}</Link>
                            {j === pand.maaktdeeluitvanVerblijfsobjects.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/pand/${pand.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/pand/${pand.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/pand/${pand.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Pands found</div>
        )}
      </div>
    </div>
  );
};

export default Pand;
