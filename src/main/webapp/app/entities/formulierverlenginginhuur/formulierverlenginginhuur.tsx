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

import { getEntities } from './formulierverlenginginhuur.reducer';

export const Formulierverlenginginhuur = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const formulierverlenginginhuurList = useAppSelector(state => state.formulierverlenginginhuur.entities);
  const loading = useAppSelector(state => state.formulierverlenginginhuur.loading);

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
      <h2 id="formulierverlenginginhuur-heading" data-cy="FormulierverlenginginhuurHeading">
        Formulierverlenginginhuurs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/formulierverlenginginhuur/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Formulierverlenginginhuur
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {formulierverlenginginhuurList && formulierverlenginginhuurList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumeindenieuw')}>
                  Datumeindenieuw <FontAwesomeIcon icon={getSortIconByFieldName('datumeindenieuw')} />
                </th>
                <th className="hand" onClick={sort('indicatieredeninhuurgewijzigd')}>
                  Indicatieredeninhuurgewijzigd <FontAwesomeIcon icon={getSortIconByFieldName('indicatieredeninhuurgewijzigd')} />
                </th>
                <th className="hand" onClick={sort('indicatieverhogeninkooporder')}>
                  Indicatieverhogeninkooporder <FontAwesomeIcon icon={getSortIconByFieldName('indicatieverhogeninkooporder')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {formulierverlenginginhuurList.map((formulierverlenginginhuur, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/formulierverlenginginhuur/${formulierverlenginginhuur.id}`} color="link" size="sm">
                      {formulierverlenginginhuur.id}
                    </Button>
                  </td>
                  <td>
                    {formulierverlenginginhuur.datumeindenieuw ? (
                      <TextFormat type="date" value={formulierverlenginginhuur.datumeindenieuw} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{formulierverlenginginhuur.indicatieredeninhuurgewijzigd}</td>
                  <td>{formulierverlenginginhuur.indicatieverhogeninkooporder}</td>
                  <td>{formulierverlenginginhuur.toelichting}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/formulierverlenginginhuur/${formulierverlenginginhuur.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/formulierverlenginginhuur/${formulierverlenginginhuur.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/formulierverlenginginhuur/${formulierverlenginginhuur.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Formulierverlenginginhuurs found</div>
        )}
      </div>
    </div>
  );
};

export default Formulierverlenginginhuur;
