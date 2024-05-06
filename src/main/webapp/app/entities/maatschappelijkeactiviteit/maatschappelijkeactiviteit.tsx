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

import { getEntities } from './maatschappelijkeactiviteit.reducer';

export const Maatschappelijkeactiviteit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const maatschappelijkeactiviteitList = useAppSelector(state => state.maatschappelijkeactiviteit.entities);
  const loading = useAppSelector(state => state.maatschappelijkeactiviteit.loading);

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
      <h2 id="maatschappelijkeactiviteit-heading" data-cy="MaatschappelijkeactiviteitHeading">
        Maatschappelijkeactiviteits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/maatschappelijkeactiviteit/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Maatschappelijkeactiviteit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {maatschappelijkeactiviteitList && maatschappelijkeactiviteitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adresbinnenland')}>
                  Adresbinnenland <FontAwesomeIcon icon={getSortIconByFieldName('adresbinnenland')} />
                </th>
                <th className="hand" onClick={sort('adrescorrespondentie')}>
                  Adrescorrespondentie <FontAwesomeIcon icon={getSortIconByFieldName('adrescorrespondentie')} />
                </th>
                <th className="hand" onClick={sort('datumaanvang')}>
                  Datumaanvang <FontAwesomeIcon icon={getSortIconByFieldName('datumaanvang')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldig')}>
                  Datumeindegeldig <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldig')} />
                </th>
                <th className="hand" onClick={sort('datumfaillisement')}>
                  Datumfaillisement <FontAwesomeIcon icon={getSortIconByFieldName('datumfaillisement')} />
                </th>
                <th className="hand" onClick={sort('indicatieeconomischactief')}>
                  Indicatieeconomischactief <FontAwesomeIcon icon={getSortIconByFieldName('indicatieeconomischactief')} />
                </th>
                <th className="hand" onClick={sort('kvknummer')}>
                  Kvknummer <FontAwesomeIcon icon={getSortIconByFieldName('kvknummer')} />
                </th>
                <th className="hand" onClick={sort('rechtsvorm')}>
                  Rechtsvorm <FontAwesomeIcon icon={getSortIconByFieldName('rechtsvorm')} />
                </th>
                <th className="hand" onClick={sort('rsin')}>
                  Rsin <FontAwesomeIcon icon={getSortIconByFieldName('rsin')} />
                </th>
                <th className="hand" onClick={sort('statutairenaam')}>
                  Statutairenaam <FontAwesomeIcon icon={getSortIconByFieldName('statutairenaam')} />
                </th>
                <th className="hand" onClick={sort('telefoonnummer')}>
                  Telefoonnummer <FontAwesomeIcon icon={getSortIconByFieldName('telefoonnummer')} />
                </th>
                <th className="hand" onClick={sort('url')}>
                  Url <FontAwesomeIcon icon={getSortIconByFieldName('url')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {maatschappelijkeactiviteitList.map((maatschappelijkeactiviteit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/maatschappelijkeactiviteit/${maatschappelijkeactiviteit.id}`} color="link" size="sm">
                      {maatschappelijkeactiviteit.id}
                    </Button>
                  </td>
                  <td>{maatschappelijkeactiviteit.adresbinnenland}</td>
                  <td>{maatschappelijkeactiviteit.adrescorrespondentie}</td>
                  <td>
                    {maatschappelijkeactiviteit.datumaanvang ? (
                      <TextFormat type="date" value={maatschappelijkeactiviteit.datumaanvang} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {maatschappelijkeactiviteit.datumeindegeldig ? (
                      <TextFormat type="date" value={maatschappelijkeactiviteit.datumeindegeldig} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {maatschappelijkeactiviteit.datumfaillisement ? (
                      <TextFormat type="date" value={maatschappelijkeactiviteit.datumfaillisement} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{maatschappelijkeactiviteit.indicatieeconomischactief}</td>
                  <td>{maatschappelijkeactiviteit.kvknummer}</td>
                  <td>{maatschappelijkeactiviteit.rechtsvorm}</td>
                  <td>{maatschappelijkeactiviteit.rsin}</td>
                  <td>{maatschappelijkeactiviteit.statutairenaam}</td>
                  <td>{maatschappelijkeactiviteit.telefoonnummer}</td>
                  <td>{maatschappelijkeactiviteit.url}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/maatschappelijkeactiviteit/${maatschappelijkeactiviteit.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/maatschappelijkeactiviteit/${maatschappelijkeactiviteit.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/maatschappelijkeactiviteit/${maatschappelijkeactiviteit.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Maatschappelijkeactiviteits found</div>
        )}
      </div>
    </div>
  );
};

export default Maatschappelijkeactiviteit;
