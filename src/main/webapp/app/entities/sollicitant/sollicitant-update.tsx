import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISollicitatiegesprek } from 'app/shared/model/sollicitatiegesprek.model';
import { getEntities as getSollicitatiegespreks } from 'app/entities/sollicitatiegesprek/sollicitatiegesprek.reducer';
import { ISollicitant } from 'app/shared/model/sollicitant.model';
import { getEntity, updateEntity, createEntity, reset } from './sollicitant.reducer';

export const SollicitantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sollicitatiegespreks = useAppSelector(state => state.sollicitatiegesprek.entities);
  const sollicitantEntity = useAppSelector(state => state.sollicitant.entity);
  const loading = useAppSelector(state => state.sollicitant.loading);
  const updating = useAppSelector(state => state.sollicitant.updating);
  const updateSuccess = useAppSelector(state => state.sollicitant.updateSuccess);

  const handleClose = () => {
    navigate('/sollicitant');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSollicitatiegespreks({}));
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
      ...sollicitantEntity,
      ...values,
      kandidaatSollicitatiegespreks: mapIdList(values.kandidaatSollicitatiegespreks),
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
          ...sollicitantEntity,
          kandidaatSollicitatiegespreks: sollicitantEntity?.kandidaatSollicitatiegespreks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sollicitant.home.createOrEditLabel" data-cy="SollicitantCreateUpdateHeading">
            Create or edit a Sollicitant
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="sollicitant-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Kandidaat Sollicitatiegesprek"
                id="sollicitant-kandidaatSollicitatiegesprek"
                data-cy="kandidaatSollicitatiegesprek"
                type="select"
                multiple
                name="kandidaatSollicitatiegespreks"
              >
                <option value="" key="0" />
                {sollicitatiegespreks
                  ? sollicitatiegespreks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sollicitant" replace color="info">
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

export default SollicitantUpdate;
