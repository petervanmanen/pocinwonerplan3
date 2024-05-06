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

import { getEntities } from './tenaamstelling.reducer';

export const Tenaamstelling = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const tenaamstellingList = useAppSelector(state => state.tenaamstelling.entities);
  const loading = useAppSelector(state => state.tenaamstelling.loading);

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
      <h2 id="tenaamstelling-heading" data-cy="TenaamstellingHeading">
        Tenaamstellings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/tenaamstelling/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Tenaamstelling
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tenaamstellingList && tenaamstellingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aandeelinrecht')}>
                  Aandeelinrecht <FontAwesomeIcon icon={getSortIconByFieldName('aandeelinrecht')} />
                </th>
                <th className="hand" onClick={sort('burgerlijkestaattentijdevanverkrijging')}>
                  Burgerlijkestaattentijdevanverkrijging{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('burgerlijkestaattentijdevanverkrijging')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheid')}>
                  Datumbegingeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheid')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheid')}>
                  Datumeindegeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheid')} />
                </th>
                <th className="hand" onClick={sort('exploitantcode')}>
                  Exploitantcode <FontAwesomeIcon icon={getSortIconByFieldName('exploitantcode')} />
                </th>
                <th className="hand" onClick={sort('identificatietenaamstelling')}>
                  Identificatietenaamstelling <FontAwesomeIcon icon={getSortIconByFieldName('identificatietenaamstelling')} />
                </th>
                <th className="hand" onClick={sort('verklaringinzakederdenbescherming')}>
                  Verklaringinzakederdenbescherming <FontAwesomeIcon icon={getSortIconByFieldName('verklaringinzakederdenbescherming')} />
                </th>
                <th className="hand" onClick={sort('verkregennamenssamenwerkingsverband')}>
                  Verkregennamenssamenwerkingsverband{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('verkregennamenssamenwerkingsverband')} />
                </th>
                <th>
                  Heeft Rechtspersoon <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tenaamstellingList.map((tenaamstelling, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tenaamstelling/${tenaamstelling.id}`} color="link" size="sm">
                      {tenaamstelling.id}
                    </Button>
                  </td>
                  <td>{tenaamstelling.aandeelinrecht}</td>
                  <td>{tenaamstelling.burgerlijkestaattentijdevanverkrijging}</td>
                  <td>
                    {tenaamstelling.datumbegingeldigheid ? (
                      <TextFormat type="date" value={tenaamstelling.datumbegingeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {tenaamstelling.datumeindegeldigheid ? (
                      <TextFormat type="date" value={tenaamstelling.datumeindegeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{tenaamstelling.exploitantcode}</td>
                  <td>{tenaamstelling.identificatietenaamstelling}</td>
                  <td>{tenaamstelling.verklaringinzakederdenbescherming}</td>
                  <td>{tenaamstelling.verkregennamenssamenwerkingsverband}</td>
                  <td>
                    {tenaamstelling.heeftRechtspersoon ? (
                      <Link to={`/rechtspersoon/${tenaamstelling.heeftRechtspersoon.id}`}>{tenaamstelling.heeftRechtspersoon.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tenaamstelling/${tenaamstelling.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/tenaamstelling/${tenaamstelling.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/tenaamstelling/${tenaamstelling.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Tenaamstellings found</div>
        )}
      </div>
    </div>
  );
};

export default Tenaamstelling;