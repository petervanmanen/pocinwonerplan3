import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPrijsafspraak } from 'app/shared/model/prijsafspraak.model';
import { getEntities as getPrijsafspraaks } from 'app/entities/prijsafspraak/prijsafspraak.reducer';
import { IPrijsregel } from 'app/shared/model/prijsregel.model';
import { getEntity, updateEntity, createEntity, reset } from './prijsregel.reducer';

export const PrijsregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const prijsafspraaks = useAppSelector(state => state.prijsafspraak.entities);
  const prijsregelEntity = useAppSelector(state => state.prijsregel.entity);
  const loading = useAppSelector(state => state.prijsregel.loading);
  const updating = useAppSelector(state => state.prijsregel.updating);
  const updateSuccess = useAppSelector(state => state.prijsregel.updateSuccess);

  const handleClose = () => {
    navigate('/prijsregel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPrijsafspraaks({}));
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...prijsregelEntity,
      ...values,
      heeftPrijsafspraak: prijsafspraaks.find(it => it.id.toString() === values.heeftPrijsafspraak?.toString()),
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
          ...prijsregelEntity,
          heeftPrijsafspraak: prijsregelEntity?.heeftPrijsafspraak?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.prijsregel.home.createOrEditLabel" data-cy="PrijsregelCreateUpdateHeading">
            Create or edit a Prijsregel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="prijsregel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bedrag" id="prijsregel-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Credit" id="prijsregel-credit" name="credit" data-cy="credit" type="text" />
              <ValidatedField
                id="prijsregel-heeftPrijsafspraak"
                name="heeftPrijsafspraak"
                data-cy="heeftPrijsafspraak"
                label="Heeft Prijsafspraak"
                type="select"
              >
                <option value="" key="0" />
                {prijsafspraaks
                  ? prijsafspraaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/prijsregel" replace color="info">
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

export default PrijsregelUpdate;
