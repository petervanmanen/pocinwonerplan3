import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOnderwijsloopbaan } from 'app/shared/model/onderwijsloopbaan.model';
import { getEntities as getOnderwijsloopbaans } from 'app/entities/onderwijsloopbaan/onderwijsloopbaan.reducer';
import { ILoopbaanstap } from 'app/shared/model/loopbaanstap.model';
import { getEntity, updateEntity, createEntity, reset } from './loopbaanstap.reducer';

export const LoopbaanstapUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const onderwijsloopbaans = useAppSelector(state => state.onderwijsloopbaan.entities);
  const loopbaanstapEntity = useAppSelector(state => state.loopbaanstap.entity);
  const loading = useAppSelector(state => state.loopbaanstap.loading);
  const updating = useAppSelector(state => state.loopbaanstap.updating);
  const updateSuccess = useAppSelector(state => state.loopbaanstap.updateSuccess);

  const handleClose = () => {
    navigate('/loopbaanstap');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOnderwijsloopbaans({}));
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
      ...loopbaanstapEntity,
      ...values,
      emptyOnderwijsloopbaan: onderwijsloopbaans.find(it => it.id.toString() === values.emptyOnderwijsloopbaan?.toString()),
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
          ...loopbaanstapEntity,
          emptyOnderwijsloopbaan: loopbaanstapEntity?.emptyOnderwijsloopbaan?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.loopbaanstap.home.createOrEditLabel" data-cy="LoopbaanstapCreateUpdateHeading">
            Create or edit a Loopbaanstap
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="loopbaanstap-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Klas" id="loopbaanstap-klas" name="klas" data-cy="klas" type="text" />
              <ValidatedField
                label="Onderwijstype"
                id="loopbaanstap-onderwijstype"
                name="onderwijstype"
                data-cy="onderwijstype"
                type="text"
              />
              <ValidatedField label="Schooljaar" id="loopbaanstap-schooljaar" name="schooljaar" data-cy="schooljaar" type="text" />
              <ValidatedField
                id="loopbaanstap-emptyOnderwijsloopbaan"
                name="emptyOnderwijsloopbaan"
                data-cy="emptyOnderwijsloopbaan"
                label="Empty Onderwijsloopbaan"
                type="select"
              >
                <option value="" key="0" />
                {onderwijsloopbaans
                  ? onderwijsloopbaans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/loopbaanstap" replace color="info">
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

export default LoopbaanstapUpdate;
