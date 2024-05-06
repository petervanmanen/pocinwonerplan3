import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPaal } from 'app/shared/model/paal.model';
import { getEntity, updateEntity, createEntity, reset } from './paal.reducer';

export const PaalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const paalEntity = useAppSelector(state => state.paal.entity);
  const loading = useAppSelector(state => state.paal.loading);
  const updating = useAppSelector(state => state.paal.updating);
  const updateSuccess = useAppSelector(state => state.paal.updateSuccess);

  const handleClose = () => {
    navigate('/paal');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...paalEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...paalEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.paal.home.createOrEditLabel" data-cy="PaalCreateUpdateHeading">
            Create or edit a Paal
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="paal-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Breedte" id="paal-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Diameter" id="paal-diameter" name="diameter" data-cy="diameter" type="text" />
              <ValidatedField label="Hoogte" id="paal-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="paal-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveauactueel"
                id="paal-kwaliteitsniveauactueel"
                name="kwaliteitsniveauactueel"
                data-cy="kwaliteitsniveauactueel"
                type="text"
              />
              <ValidatedField
                label="Kwaliteitsniveaugewenst"
                id="paal-kwaliteitsniveaugewenst"
                name="kwaliteitsniveaugewenst"
                data-cy="kwaliteitsniveaugewenst"
                type="text"
              />
              <ValidatedField label="Lengte" id="paal-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Leverancier" id="paal-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField label="Materiaal" id="paal-materiaal" name="materiaal" data-cy="materiaal" type="text" />
              <ValidatedField label="Vorm" id="paal-vorm" name="vorm" data-cy="vorm" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/paal" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PaalUpdate;
