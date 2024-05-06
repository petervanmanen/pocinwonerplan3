import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFormulierinhuur } from 'app/shared/model/formulierinhuur.model';
import { getEntity, updateEntity, createEntity, reset } from './formulierinhuur.reducer';

export const FormulierinhuurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const formulierinhuurEntity = useAppSelector(state => state.formulierinhuur.entity);
  const loading = useAppSelector(state => state.formulierinhuur.loading);
  const updating = useAppSelector(state => state.formulierinhuur.updating);
  const updateSuccess = useAppSelector(state => state.formulierinhuur.updateSuccess);

  const handleClose = () => {
    navigate('/formulierinhuur');
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
      ...formulierinhuurEntity,
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
          ...formulierinhuurEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.formulierinhuur.home.createOrEditLabel" data-cy="FormulierinhuurCreateUpdateHeading">
            Create or edit a Formulierinhuur
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
                <ValidatedField name="id" required readOnly id="formulierinhuur-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Akkoordfinancieeladviseur"
                id="formulierinhuur-akkoordfinancieeladviseur"
                name="akkoordfinancieeladviseur"
                data-cy="akkoordfinancieeladviseur"
                type="text"
              />
              <ValidatedField
                label="Akkoordhradviseur"
                id="formulierinhuur-akkoordhradviseur"
                name="akkoordhradviseur"
                data-cy="akkoordhradviseur"
                type="text"
              />
              <ValidatedField
                label="Datuminganginhuur"
                id="formulierinhuur-datuminganginhuur"
                name="datuminganginhuur"
                data-cy="datuminganginhuur"
                type="date"
              />
              <ValidatedField
                label="Functienaaminhuur"
                id="formulierinhuur-functienaaminhuur"
                name="functienaaminhuur"
                data-cy="functienaaminhuur"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/formulierinhuur" replace color="info">
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

export default FormulierinhuurUpdate;
