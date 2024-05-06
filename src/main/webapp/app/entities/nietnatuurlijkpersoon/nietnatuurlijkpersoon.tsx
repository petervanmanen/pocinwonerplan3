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

import { getEntities } from './nietnatuurlijkpersoon.reducer';

export const Nietnatuurlijkpersoon = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const nietnatuurlijkpersoonList = useAppSelector(state => state.nietnatuurlijkpersoon.entities);
  const loading = useAppSelector(state => state.nietnatuurlijkpersoon.loading);

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
      <h2 id="nietnatuurlijkpersoon-heading" data-cy="NietnatuurlijkpersoonHeading">
        Nietnatuurlijkpersoons
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/nietnatuurlijkpersoon/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Nietnatuurlijkpersoon
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {nietnatuurlijkpersoonList && nietnatuurlijkpersoonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumaanvang')}>
                  Datumaanvang <FontAwesomeIcon icon={getSortIconByFieldName('datumaanvang')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumuitschrijving')}>
                  Datumuitschrijving <FontAwesomeIcon icon={getSortIconByFieldName('datumuitschrijving')} />
                </th>
                <th className="hand" onClick={sort('datumvoortzetting')}>
                  Datumvoortzetting <FontAwesomeIcon icon={getSortIconByFieldName('datumvoortzetting')} />
                </th>
                <th className="hand" onClick={sort('faxnummer')}>
                  Faxnummer <FontAwesomeIcon icon={getSortIconByFieldName('faxnummer')} />
                </th>
                <th className="hand" onClick={sort('ingeschreven')}>
                  Ingeschreven <FontAwesomeIcon icon={getSortIconByFieldName('ingeschreven')} />
                </th>
                <th className="hand" onClick={sort('inoprichting')}>
                  Inoprichting <FontAwesomeIcon icon={getSortIconByFieldName('inoprichting')} />
                </th>
                <th className="hand" onClick={sort('kvknummer')}>
                  Kvknummer <FontAwesomeIcon icon={getSortIconByFieldName('kvknummer')} />
                </th>
                <th className="hand" onClick={sort('nnpid')}>
                  Nnpid <FontAwesomeIcon icon={getSortIconByFieldName('nnpid')} />
                </th>
                <th className="hand" onClick={sort('rechtsvorm')}>
                  Rechtsvorm <FontAwesomeIcon icon={getSortIconByFieldName('rechtsvorm')} />
                </th>
                <th className="hand" onClick={sort('rsinnummer')}>
                  Rsinnummer <FontAwesomeIcon icon={getSortIconByFieldName('rsinnummer')} />
                </th>
                <th className="hand" onClick={sort('statutairenaam')}>
                  Statutairenaam <FontAwesomeIcon icon={getSortIconByFieldName('statutairenaam')} />
                </th>
                <th className="hand" onClick={sort('statutairezetel')}>
                  Statutairezetel <FontAwesomeIcon icon={getSortIconByFieldName('statutairezetel')} />
                </th>
                <th className="hand" onClick={sort('websiteurl')}>
                  Websiteurl <FontAwesomeIcon icon={getSortIconByFieldName('websiteurl')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {nietnatuurlijkpersoonList.map((nietnatuurlijkpersoon, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/nietnatuurlijkpersoon/${nietnatuurlijkpersoon.id}`} color="link" size="sm">
                      {nietnatuurlijkpersoon.id}
                    </Button>
                  </td>
                  <td>
                    {nietnatuurlijkpersoon.datumaanvang ? (
                      <TextFormat type="date" value={nietnatuurlijkpersoon.datumaanvang} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {nietnatuurlijkpersoon.datumeinde ? (
                      <TextFormat type="date" value={nietnatuurlijkpersoon.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {nietnatuurlijkpersoon.datumuitschrijving ? (
                      <TextFormat type="date" value={nietnatuurlijkpersoon.datumuitschrijving} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {nietnatuurlijkpersoon.datumvoortzetting ? (
                      <TextFormat type="date" value={nietnatuurlijkpersoon.datumvoortzetting} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{nietnatuurlijkpersoon.faxnummer}</td>
                  <td>{nietnatuurlijkpersoon.ingeschreven ? 'true' : 'false'}</td>
                  <td>{nietnatuurlijkpersoon.inoprichting ? 'true' : 'false'}</td>
                  <td>{nietnatuurlijkpersoon.kvknummer}</td>
                  <td>{nietnatuurlijkpersoon.nnpid}</td>
                  <td>{nietnatuurlijkpersoon.rechtsvorm}</td>
                  <td>{nietnatuurlijkpersoon.rsinnummer}</td>
                  <td>{nietnatuurlijkpersoon.statutairenaam}</td>
                  <td>{nietnatuurlijkpersoon.statutairezetel}</td>
                  <td>{nietnatuurlijkpersoon.websiteurl}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/nietnatuurlijkpersoon/${nietnatuurlijkpersoon.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/nietnatuurlijkpersoon/${nietnatuurlijkpersoon.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/nietnatuurlijkpersoon/${nietnatuurlijkpersoon.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Nietnatuurlijkpersoons found</div>
        )}
      </div>
    </div>
  );
};

export default Nietnatuurlijkpersoon;
