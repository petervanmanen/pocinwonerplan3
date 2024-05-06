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

import { getEntities } from './sbiactiviteit.reducer';

export const Sbiactiviteit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const sbiactiviteitList = useAppSelector(state => state.sbiactiviteit.entities);
  const loading = useAppSelector(state => state.sbiactiviteit.loading);

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
      <h2 id="sbiactiviteit-heading" data-cy="SbiactiviteitHeading">
        Sbiactiviteits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/sbiactiviteit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Sbiactiviteit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sbiactiviteitList && sbiactiviteitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumeindesbiactiviteit')}>
                  Datumeindesbiactiviteit <FontAwesomeIcon icon={getSortIconByFieldName('datumeindesbiactiviteit')} />
                </th>
                <th className="hand" onClick={sort('datumingangsbiactiviteit')}>
                  Datumingangsbiactiviteit <FontAwesomeIcon icon={getSortIconByFieldName('datumingangsbiactiviteit')} />
                </th>
                <th className="hand" onClick={sort('hoofdniveau')}>
                  Hoofdniveau <FontAwesomeIcon icon={getSortIconByFieldName('hoofdniveau')} />
                </th>
                <th className="hand" onClick={sort('hoofdniveauomschrijving')}>
                  Hoofdniveauomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('hoofdniveauomschrijving')} />
                </th>
                <th className="hand" onClick={sort('naamactiviteit')}>
                  Naamactiviteit <FontAwesomeIcon icon={getSortIconByFieldName('naamactiviteit')} />
                </th>
                <th className="hand" onClick={sort('sbicode')}>
                  Sbicode <FontAwesomeIcon icon={getSortIconByFieldName('sbicode')} />
                </th>
                <th className="hand" onClick={sort('sbigroep')}>
                  Sbigroep <FontAwesomeIcon icon={getSortIconByFieldName('sbigroep')} />
                </th>
                <th className="hand" onClick={sort('sbigroepomschrijving')}>
                  Sbigroepomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('sbigroepomschrijving')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sbiactiviteitList.map((sbiactiviteit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sbiactiviteit/${sbiactiviteit.id}`} color="link" size="sm">
                      {sbiactiviteit.id}
                    </Button>
                  </td>
                  <td>
                    {sbiactiviteit.datumeindesbiactiviteit ? (
                      <TextFormat type="date" value={sbiactiviteit.datumeindesbiactiviteit} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {sbiactiviteit.datumingangsbiactiviteit ? (
                      <TextFormat type="date" value={sbiactiviteit.datumingangsbiactiviteit} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{sbiactiviteit.hoofdniveau}</td>
                  <td>{sbiactiviteit.hoofdniveauomschrijving}</td>
                  <td>{sbiactiviteit.naamactiviteit}</td>
                  <td>{sbiactiviteit.sbicode}</td>
                  <td>{sbiactiviteit.sbigroep}</td>
                  <td>{sbiactiviteit.sbigroepomschrijving}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/sbiactiviteit/${sbiactiviteit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sbiactiviteit/${sbiactiviteit.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/sbiactiviteit/${sbiactiviteit.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Sbiactiviteits found</div>
        )}
      </div>
    </div>
  );
};

export default Sbiactiviteit;
