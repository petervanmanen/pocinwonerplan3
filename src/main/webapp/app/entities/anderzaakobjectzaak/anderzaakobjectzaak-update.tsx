import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAnderzaakobjectzaak } from 'app/shared/model/anderzaakobjectzaak.model';
import { getEntity, updateEntity, createEntity, reset } from './anderzaakobjectzaak.reducer';

export const AnderzaakobjectzaakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const anderzaakobjectzaakEntity = useAppSelector(state => state.anderzaakobjectzaak.entity);
  const loading = useAppSelector(state => state.anderzaakobjectzaak.loading);
  const updating = useAppSelector(state => state.anderzaakobjectzaak.updating);
  const updateSuccess = useAppSelector(state => state.anderzaakobjectzaak.updateSuccess);

  const handleClose = () => {
    navigate('/anderzaakobjectzaak');
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
      ...anderzaakobjectzaakEntity,
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
          ...anderzaakobjectzaakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.anderzaakobjectzaak.home.createOrEditLabel" data-cy="AnderzaakobjectzaakCreateUpdateHeading">
            Create or edit a Anderzaakobjectzaak
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
                <ValidatedField name="id" required readOnly id="anderzaakobjectzaak-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Anderzaakobjectaanduiding"
                id="anderzaakobjectzaak-anderzaakobjectaanduiding"
                name="anderzaakobjectaanduiding"
                data-cy="anderzaakobjectaanduiding"
                type="text"
              />
              <ValidatedField
                label="Anderzaakobjectlocatie"
                id="anderzaakobjectzaak-anderzaakobjectlocatie"
                name="anderzaakobjectlocatie"
                data-cy="anderzaakobjectlocatie"
                type="text"
              />
              <ValidatedField
                label="Anderzaakobjectomschrijving"
                id="anderzaakobjectzaak-anderzaakobjectomschrijving"
                name="anderzaakobjectomschrijving"
                data-cy="anderzaakobjectomschrijving"
                type="text"
              />
              <ValidatedField
                label="Anderzaakobjectregistratie"
                id="anderzaakobjectzaak-anderzaakobjectregistratie"
                name="anderzaakobjectregistratie"
                data-cy="anderzaakobjectregistratie"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/anderzaakobjectzaak" replace color="info">
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

export default AnderzaakobjectzaakUpdate;
