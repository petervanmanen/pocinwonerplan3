import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGeboorteingeschrevenpersoon } from 'app/shared/model/geboorteingeschrevenpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './geboorteingeschrevenpersoon.reducer';

export const GeboorteingeschrevenpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const geboorteingeschrevenpersoonEntity = useAppSelector(state => state.geboorteingeschrevenpersoon.entity);
  const loading = useAppSelector(state => state.geboorteingeschrevenpersoon.loading);
  const updating = useAppSelector(state => state.geboorteingeschrevenpersoon.updating);
  const updateSuccess = useAppSelector(state => state.geboorteingeschrevenpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/geboorteingeschrevenpersoon');
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
      ...geboorteingeschrevenpersoonEntity,
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
          ...geboorteingeschrevenpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.geboorteingeschrevenpersoon.home.createOrEditLabel" data-cy="GeboorteingeschrevenpersoonCreateUpdateHeading">
            Create or edit a Geboorteingeschrevenpersoon
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
                <ValidatedField name="id" required readOnly id="geboorteingeschrevenpersoon-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumgeboorte"
                id="geboorteingeschrevenpersoon-datumgeboorte"
                name="datumgeboorte"
                data-cy="datumgeboorte"
                type="text"
              />
              <ValidatedField
                label="Geboorteland"
                id="geboorteingeschrevenpersoon-geboorteland"
                name="geboorteland"
                data-cy="geboorteland"
                type="text"
              />
              <ValidatedField
                label="Geboorteplaats"
                id="geboorteingeschrevenpersoon-geboorteplaats"
                name="geboorteplaats"
                data-cy="geboorteplaats"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/geboorteingeschrevenpersoon" replace color="info">
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

export default GeboorteingeschrevenpersoonUpdate;
