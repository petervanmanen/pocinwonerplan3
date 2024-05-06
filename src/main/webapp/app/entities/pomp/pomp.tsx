import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './pomp.reducer';

export const Pomp = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const pompList = useAppSelector(state => state.pomp.entities);
  const loading = useAppSelector(state => state.pomp.loading);

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
      <h2 id="pomp-heading" data-cy="PompHeading">
        Pomps
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/pomp/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Pomp
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pompList && pompList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanslagniveau')}>
                  Aanslagniveau <FontAwesomeIcon icon={getSortIconByFieldName('aanslagniveau')} />
                </th>
                <th className="hand" onClick={sort('beginstanddraaiurenteller')}>
                  Beginstanddraaiurenteller <FontAwesomeIcon icon={getSortIconByFieldName('beginstanddraaiurenteller')} />
                </th>
                <th className="hand" onClick={sort('besturingskast')}>
                  Besturingskast <FontAwesomeIcon icon={getSortIconByFieldName('besturingskast')} />
                </th>
                <th className="hand" onClick={sort('laatstestanddraaiurenteller')}>
                  Laatstestanddraaiurenteller <FontAwesomeIcon icon={getSortIconByFieldName('laatstestanddraaiurenteller')} />
                </th>
                <th className="hand" onClick={sort('laatstestandkwhteller')}>
                  Laatstestandkwhteller <FontAwesomeIcon icon={getSortIconByFieldName('laatstestandkwhteller')} />
                </th>
                <th className="hand" onClick={sort('levensduur')}>
                  Levensduur <FontAwesomeIcon icon={getSortIconByFieldName('levensduur')} />
                </th>
                <th className="hand" onClick={sort('model')}>
                  Model <FontAwesomeIcon icon={getSortIconByFieldName('model')} />
                </th>
                <th className="hand" onClick={sort('motorvermogen')}>
                  Motorvermogen <FontAwesomeIcon icon={getSortIconByFieldName('motorvermogen')} />
                </th>
                <th className="hand" onClick={sort('onderdeelmetpomp')}>
                  Onderdeelmetpomp <FontAwesomeIcon icon={getSortIconByFieldName('onderdeelmetpomp')} />
                </th>
                <th className="hand" onClick={sort('ontwerpcapaciteit')}>
                  Ontwerpcapaciteit <FontAwesomeIcon icon={getSortIconByFieldName('ontwerpcapaciteit')} />
                </th>
                <th className="hand" onClick={sort('pompcapaciteit')}>
                  Pompcapaciteit <FontAwesomeIcon icon={getSortIconByFieldName('pompcapaciteit')} />
                </th>
                <th className="hand" onClick={sort('serienummer')}>
                  Serienummer <FontAwesomeIcon icon={getSortIconByFieldName('serienummer')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('typeonderdeelmetpomp')}>
                  Typeonderdeelmetpomp <FontAwesomeIcon icon={getSortIconByFieldName('typeonderdeelmetpomp')} />
                </th>
                <th className="hand" onClick={sort('typeplus')}>
                  Typeplus <FontAwesomeIcon icon={getSortIconByFieldName('typeplus')} />
                </th>
                <th className="hand" onClick={sort('typewaaier')}>
                  Typewaaier <FontAwesomeIcon icon={getSortIconByFieldName('typewaaier')} />
                </th>
                <th className="hand" onClick={sort('uitslagpeil')}>
                  Uitslagpeil <FontAwesomeIcon icon={getSortIconByFieldName('uitslagpeil')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pompList.map((pomp, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pomp/${pomp.id}`} color="link" size="sm">
                      {pomp.id}
                    </Button>
                  </td>
                  <td>{pomp.aanslagniveau}</td>
                  <td>{pomp.beginstanddraaiurenteller}</td>
                  <td>{pomp.besturingskast}</td>
                  <td>{pomp.laatstestanddraaiurenteller}</td>
                  <td>{pomp.laatstestandkwhteller}</td>
                  <td>{pomp.levensduur}</td>
                  <td>{pomp.model}</td>
                  <td>{pomp.motorvermogen}</td>
                  <td>{pomp.onderdeelmetpomp}</td>
                  <td>{pomp.ontwerpcapaciteit}</td>
                  <td>{pomp.pompcapaciteit}</td>
                  <td>{pomp.serienummer}</td>
                  <td>{pomp.type}</td>
                  <td>{pomp.typeonderdeelmetpomp}</td>
                  <td>{pomp.typeplus}</td>
                  <td>{pomp.typewaaier}</td>
                  <td>{pomp.uitslagpeil}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/pomp/${pomp.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/pomp/${pomp.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/pomp/${pomp.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Pomps found</div>
        )}
      </div>
    </div>
  );
};

export default Pomp;
