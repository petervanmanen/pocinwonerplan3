import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOorspronkelijkefunctie } from 'app/shared/model/oorspronkelijkefunctie.model';
import { getEntity, updateEntity, createEntity, reset } from './oorspronkelijkefunctie.reducer';

export const OorspronkelijkefunctieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const oorspronkelijkefunctieEntity = useAppSelector(state => state.oorspronkelijkefunctie.entity);
  const loading = useAppSelector(state => state.oorspronkelijkefunctie.loading);
  const updating = useAppSelector(state => state.oorspronkelijkefunctie.updating);
  const updateSuccess = useAppSelector(state => state.oorspronkelijkefunctie.updateSuccess);

  const handleClose = () => {
    navigate('/oorspronkelijkefunctie');
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
      ...oorspronkelijkefunctieEntity,
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
          ...oorspronkelijkefunctieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.oorspronkelijkefunctie.home.createOrEditLabel" data-cy="OorspronkelijkefunctieCreateUpdateHeading">
            Create or edit a Oorspronkelijkefunctie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="oorspronkelijkefunctie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Functie" id="oorspronkelijkefunctie-functie" name="functie" data-cy="functie" type="text" />
              <ValidatedField
                label="Functiesoort"
                id="oorspronkelijkefunctie-functiesoort"
                name="functiesoort"
                data-cy="functiesoort"
                type="text"
              />
              <ValidatedField
                label="Hoofdcategorie"
                id="oorspronkelijkefunctie-hoofdcategorie"
                name="hoofdcategorie"
                data-cy="hoofdcategorie"
                type="text"
              />
              <ValidatedField
                label="Hoofdfunctie"
                id="oorspronkelijkefunctie-hoofdfunctie"
                name="hoofdfunctie"
                data-cy="hoofdfunctie"
                type="text"
              />
              <ValidatedField
                label="Subcategorie"
                id="oorspronkelijkefunctie-subcategorie"
                name="subcategorie"
                data-cy="subcategorie"
                type="text"
              />
              <ValidatedField
                label="Toelichting"
                id="oorspronkelijkefunctie-toelichting"
                name="toelichting"
                data-cy="toelichting"
                type="text"
              />
              <ValidatedField
                label="Verbijzondering"
                id="oorspronkelijkefunctie-verbijzondering"
                name="verbijzondering"
                data-cy="verbijzondering"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/oorspronkelijkefunctie" replace color="info">
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

export default OorspronkelijkefunctieUpdate;
