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

import { getEntities } from './declaratieregel.reducer';

export const Declaratieregel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const declaratieregelList = useAppSelector(state => state.declaratieregel.entities);
  const loading = useAppSelector(state => state.declaratieregel.loading);

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
      <h2 id="declaratieregel-heading" data-cy="DeclaratieregelHeading">
        Declaratieregels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/declaratieregel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Declaratieregel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {declaratieregelList && declaratieregelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bedrag')}>
                  Bedrag <FontAwesomeIcon icon={getSortIconByFieldName('bedrag')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  Code <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th>
                  Isvoor Beschikking <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Client <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Valtbinnen Declaratie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isopbasisvan Toewijzing <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {declaratieregelList.map((declaratieregel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/declaratieregel/${declaratieregel.id}`} color="link" size="sm">
                      {declaratieregel.id}
                    </Button>
                  </td>
                  <td>{declaratieregel.bedrag}</td>
                  <td>{declaratieregel.code}</td>
                  <td>
                    {declaratieregel.datumeinde ? (
                      <TextFormat type="date" value={declaratieregel.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {declaratieregel.datumstart ? (
                      <TextFormat type="date" value={declaratieregel.datumstart} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {declaratieregel.isvoorBeschikking ? (
                      <Link to={`/beschikking/${declaratieregel.isvoorBeschikking.id}`}>{declaratieregel.isvoorBeschikking.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {declaratieregel.betreftClient ? (
                      <Link to={`/client/${declaratieregel.betreftClient.id}`}>{declaratieregel.betreftClient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {declaratieregel.valtbinnenDeclaratie ? (
                      <Link to={`/declaratie/${declaratieregel.valtbinnenDeclaratie.id}`}>{declaratieregel.valtbinnenDeclaratie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {declaratieregel.isopbasisvanToewijzing ? (
                      <Link to={`/toewijzing/${declaratieregel.isopbasisvanToewijzing.id}`}>
                        {declaratieregel.isopbasisvanToewijzing.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/declaratieregel/${declaratieregel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/declaratieregel/${declaratieregel.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/declaratieregel/${declaratieregel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Declaratieregels found</div>
        )}
      </div>
    </div>
  );
};

export default Declaratieregel;
