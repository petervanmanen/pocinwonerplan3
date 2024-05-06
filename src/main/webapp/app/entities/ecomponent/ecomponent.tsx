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

import { getEntities } from './ecomponent.reducer';

export const Ecomponent = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const ecomponentList = useAppSelector(state => state.ecomponent.entities);
  const loading = useAppSelector(state => state.ecomponent.loading);

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
      <h2 id="ecomponent-heading" data-cy="EcomponentHeading">
        Ecomponents
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/ecomponent/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Ecomponent
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ecomponentList && ecomponentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bedrag')}>
                  Bedrag <FontAwesomeIcon icon={getSortIconByFieldName('bedrag')} />
                </th>
                <th className="hand" onClick={sort('datumbeginbetrekkingop')}>
                  Datumbeginbetrekkingop <FontAwesomeIcon icon={getSortIconByFieldName('datumbeginbetrekkingop')} />
                </th>
                <th className="hand" onClick={sort('datumeindebetrekkingop')}>
                  Datumeindebetrekkingop <FontAwesomeIcon icon={getSortIconByFieldName('datumeindebetrekkingop')} />
                </th>
                <th className="hand" onClick={sort('debetcredit')}>
                  Debetcredit <FontAwesomeIcon icon={getSortIconByFieldName('debetcredit')} />
                </th>
                <th className="hand" onClick={sort('groep')}>
                  Groep <FontAwesomeIcon icon={getSortIconByFieldName('groep')} />
                </th>
                <th className="hand" onClick={sort('groepcode')}>
                  Groepcode <FontAwesomeIcon icon={getSortIconByFieldName('groepcode')} />
                </th>
                <th className="hand" onClick={sort('grootboekcode')}>
                  Grootboekcode <FontAwesomeIcon icon={getSortIconByFieldName('grootboekcode')} />
                </th>
                <th className="hand" onClick={sort('grootboekomschrijving')}>
                  Grootboekomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('grootboekomschrijving')} />
                </th>
                <th className="hand" onClick={sort('kostenplaats')}>
                  Kostenplaats <FontAwesomeIcon icon={getSortIconByFieldName('kostenplaats')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('rekeningnummer')}>
                  Rekeningnummer <FontAwesomeIcon icon={getSortIconByFieldName('rekeningnummer')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ecomponentList.map((ecomponent, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ecomponent/${ecomponent.id}`} color="link" size="sm">
                      {ecomponent.id}
                    </Button>
                  </td>
                  <td>{ecomponent.bedrag}</td>
                  <td>
                    {ecomponent.datumbeginbetrekkingop ? (
                      <TextFormat type="date" value={ecomponent.datumbeginbetrekkingop} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {ecomponent.datumeindebetrekkingop ? (
                      <TextFormat type="date" value={ecomponent.datumeindebetrekkingop} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{ecomponent.debetcredit}</td>
                  <td>{ecomponent.groep}</td>
                  <td>{ecomponent.groepcode}</td>
                  <td>{ecomponent.grootboekcode}</td>
                  <td>{ecomponent.grootboekomschrijving}</td>
                  <td>{ecomponent.kostenplaats}</td>
                  <td>{ecomponent.omschrijving}</td>
                  <td>{ecomponent.rekeningnummer}</td>
                  <td>{ecomponent.toelichting}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/ecomponent/${ecomponent.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/ecomponent/${ecomponent.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/ecomponent/${ecomponent.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Ecomponents found</div>
        )}
      </div>
    </div>
  );
};

export default Ecomponent;
