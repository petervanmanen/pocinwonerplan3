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

import { getEntities } from './eobjecttype.reducer';

export const Eobjecttype = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const eobjecttypeList = useAppSelector(state => state.eobjecttype.entities);
  const loading = useAppSelector(state => state.eobjecttype.loading);

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
      <h2 id="eobjecttype-heading" data-cy="EobjecttypeHeading">
        Eobjecttypes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/eobjecttype/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Eobjecttype
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {eobjecttypeList && eobjecttypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('datumopname')}>
                  Datumopname <FontAwesomeIcon icon={getSortIconByFieldName('datumopname')} />
                </th>
                <th className="hand" onClick={sort('definitie')}>
                  Definitie <FontAwesomeIcon icon={getSortIconByFieldName('definitie')} />
                </th>
                <th className="hand" onClick={sort('eaguid')}>
                  Eaguid <FontAwesomeIcon icon={getSortIconByFieldName('eaguid')} />
                </th>
                <th className="hand" onClick={sort('herkomst')}>
                  Herkomst <FontAwesomeIcon icon={getSortIconByFieldName('herkomst')} />
                </th>
                <th className="hand" onClick={sort('herkomstdefinitie')}>
                  Herkomstdefinitie <FontAwesomeIcon icon={getSortIconByFieldName('herkomstdefinitie')} />
                </th>
                <th className="hand" onClick={sort('id')}>
                  Id <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('indicatieabstract')}>
                  Indicatieabstract <FontAwesomeIcon icon={getSortIconByFieldName('indicatieabstract')} />
                </th>
                <th className="hand" onClick={sort('kwaliteit')}>
                  Kwaliteit <FontAwesomeIcon icon={getSortIconByFieldName('kwaliteit')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('populatie')}>
                  Populatie <FontAwesomeIcon icon={getSortIconByFieldName('populatie')} />
                </th>
                <th className="hand" onClick={sort('stereotype')}>
                  Stereotype <FontAwesomeIcon icon={getSortIconByFieldName('stereotype')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th className="hand" onClick={sort('uniekeaanduiding')}>
                  Uniekeaanduiding <FontAwesomeIcon icon={getSortIconByFieldName('uniekeaanduiding')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {eobjecttypeList.map((eobjecttype, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/eobjecttype/${eobjecttype.id}`} color="link" size="sm">
                      {eobjecttype.id}
                    </Button>
                  </td>
                  <td>
                    {eobjecttype.datumopname ? (
                      <TextFormat type="date" value={eobjecttype.datumopname} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{eobjecttype.definitie}</td>
                  <td>{eobjecttype.eaguid}</td>
                  <td>{eobjecttype.herkomst}</td>
                  <td>{eobjecttype.herkomstdefinitie}</td>
                  <td>{eobjecttype.indicatieabstract ? 'true' : 'false'}</td>
                  <td>{eobjecttype.kwaliteit}</td>
                  <td>{eobjecttype.naam}</td>
                  <td>{eobjecttype.populatie}</td>
                  <td>{eobjecttype.stereotype}</td>
                  <td>{eobjecttype.toelichting}</td>
                  <td>{eobjecttype.uniekeaanduiding}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/eobjecttype/${eobjecttype.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/eobjecttype/${eobjecttype.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/eobjecttype/${eobjecttype.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Eobjecttypes found</div>
        )}
      </div>
    </div>
  );
};

export default Eobjecttype;
