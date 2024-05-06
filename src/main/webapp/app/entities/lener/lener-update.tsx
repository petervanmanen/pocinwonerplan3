import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBruikleen } from 'app/shared/model/bruikleen.model';
import { getEntities as getBruikleens } from 'app/entities/bruikleen/bruikleen.reducer';
import { ILener } from 'app/shared/model/lener.model';
import { getEntity, updateEntity, createEntity, reset } from './lener.reducer';

export const LenerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bruikleens = useAppSelector(state => state.bruikleen.entities);
  const lenerEntity = useAppSelector(state => state.lener.entity);
  const loading = useAppSelector(state => state.lener.loading);
  const updating = useAppSelector(state => state.lener.updating);
  const updateSuccess = useAppSelector(state => state.lener.updateSuccess);

  const handleClose = () => {
    navigate('/lener');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBruikleens({}));
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
      ...lenerEntity,
      ...values,
      isBruikleens: mapIdList(values.isBruikleens),
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
          ...lenerEntity,
          isBruikleens: lenerEntity?.isBruikleens?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.lener.home.createOrEditLabel" data-cy="LenerCreateUpdateHeading">
            Create or edit a Lener
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="lener-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Opmerkingen" id="lener-opmerkingen" name="opmerkingen" data-cy="opmerkingen" type="text" />
              <ValidatedField label="Is Bruikleen" id="lener-isBruikleen" data-cy="isBruikleen" type="select" multiple name="isBruikleens">
                <option value="" key="0" />
                {bruikleens
                  ? bruikleens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lener" replace color="info">
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

export default LenerUpdate;
