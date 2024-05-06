import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBalieverkoopentreekaart } from 'app/shared/model/balieverkoopentreekaart.model';
import { getEntity, updateEntity, createEntity, reset } from './balieverkoopentreekaart.reducer';

export const BalieverkoopentreekaartUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const balieverkoopentreekaartEntity = useAppSelector(state => state.balieverkoopentreekaart.entity);
  const loading = useAppSelector(state => state.balieverkoopentreekaart.loading);
  const updating = useAppSelector(state => state.balieverkoopentreekaart.updating);
  const updateSuccess = useAppSelector(state => state.balieverkoopentreekaart.updateSuccess);

  const handleClose = () => {
    navigate('/balieverkoopentreekaart');
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
      ...balieverkoopentreekaartEntity,
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
          ...balieverkoopentreekaartEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.balieverkoopentreekaart.home.createOrEditLabel" data-cy="BalieverkoopentreekaartCreateUpdateHeading">
            Create or edit a Balieverkoopentreekaart
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
                <ValidatedField name="id" required readOnly id="balieverkoopentreekaart-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumeindegeldigheid"
                id="balieverkoopentreekaart-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="text"
              />
              <ValidatedField
                label="Datumstart"
                id="balieverkoopentreekaart-datumstart"
                name="datumstart"
                data-cy="datumstart"
                type="text"
              />
              <ValidatedField
                label="Gebruiktop"
                id="balieverkoopentreekaart-gebruiktop"
                name="gebruiktop"
                data-cy="gebruiktop"
                type="text"
              />
              <ValidatedField
                label="Rondleiding"
                id="balieverkoopentreekaart-rondleiding"
                name="rondleiding"
                data-cy="rondleiding"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/balieverkoopentreekaart" replace color="info">
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

export default BalieverkoopentreekaartUpdate;
