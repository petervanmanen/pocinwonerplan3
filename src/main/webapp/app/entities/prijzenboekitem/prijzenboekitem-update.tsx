import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPrijzenboekitem } from 'app/shared/model/prijzenboekitem.model';
import { getEntity, updateEntity, createEntity, reset } from './prijzenboekitem.reducer';

export const PrijzenboekitemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const prijzenboekitemEntity = useAppSelector(state => state.prijzenboekitem.entity);
  const loading = useAppSelector(state => state.prijzenboekitem.loading);
  const updating = useAppSelector(state => state.prijzenboekitem.updating);
  const updateSuccess = useAppSelector(state => state.prijzenboekitem.updateSuccess);

  const handleClose = () => {
    navigate('/prijzenboekitem');
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
    if (values.prijs !== undefined && typeof values.prijs !== 'number') {
      values.prijs = Number(values.prijs);
    }

    const entity = {
      ...prijzenboekitemEntity,
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
          ...prijzenboekitemEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.prijzenboekitem.home.createOrEditLabel" data-cy="PrijzenboekitemCreateUpdateHeading">
            Create or edit a Prijzenboekitem
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
                <ValidatedField name="id" required readOnly id="prijzenboekitem-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumeindegeldigheid"
                id="prijzenboekitem-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumstart" id="prijzenboekitem-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Prijs" id="prijzenboekitem-prijs" name="prijs" data-cy="prijs" type="text" />
              <ValidatedField label="Verrichting" id="prijzenboekitem-verrichting" name="verrichting" data-cy="verrichting" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/prijzenboekitem" replace color="info">
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

export default PrijzenboekitemUpdate;
