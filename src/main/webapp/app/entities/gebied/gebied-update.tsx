import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBuurt } from 'app/shared/model/buurt.model';
import { getEntities as getBuurts } from 'app/entities/buurt/buurt.reducer';
import { INummeraanduiding } from 'app/shared/model/nummeraanduiding.model';
import { getEntities as getNummeraanduidings } from 'app/entities/nummeraanduiding/nummeraanduiding.reducer';
import { IGebied } from 'app/shared/model/gebied.model';
import { getEntity, updateEntity, createEntity, reset } from './gebied.reducer';

export const GebiedUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const buurts = useAppSelector(state => state.buurt.entities);
  const nummeraanduidings = useAppSelector(state => state.nummeraanduiding.entities);
  const gebiedEntity = useAppSelector(state => state.gebied.entity);
  const loading = useAppSelector(state => state.gebied.loading);
  const updating = useAppSelector(state => state.gebied.updating);
  const updateSuccess = useAppSelector(state => state.gebied.updateSuccess);

  const handleClose = () => {
    navigate('/gebied');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBuurts({}));
    dispatch(getNummeraanduidings({}));
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
      ...gebiedEntity,
      ...values,
      komtovereenBuurt: buurts.find(it => it.id.toString() === values.komtovereenBuurt?.toString()),
      ligtinNummeraanduidings: mapIdList(values.ligtinNummeraanduidings),
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
          ...gebiedEntity,
          komtovereenBuurt: gebiedEntity?.komtovereenBuurt?.id,
          ligtinNummeraanduidings: gebiedEntity?.ligtinNummeraanduidings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.gebied.home.createOrEditLabel" data-cy="GebiedCreateUpdateHeading">
            Create or edit a Gebied
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="gebied-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Gebied" id="gebied-gebied" name="gebied" data-cy="gebied" type="text" />
              <ValidatedField
                id="gebied-komtovereenBuurt"
                name="komtovereenBuurt"
                data-cy="komtovereenBuurt"
                label="Komtovereen Buurt"
                type="select"
              >
                <option value="" key="0" />
                {buurts
                  ? buurts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Ligtin Nummeraanduiding"
                id="gebied-ligtinNummeraanduiding"
                data-cy="ligtinNummeraanduiding"
                type="select"
                multiple
                name="ligtinNummeraanduidings"
              >
                <option value="" key="0" />
                {nummeraanduidings
                  ? nummeraanduidings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gebied" replace color="info">
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

export default GebiedUpdate;
