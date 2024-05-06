import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IWinkelvoorraaditem } from 'app/shared/model/winkelvoorraaditem.model';
import { getEntity, updateEntity, createEntity, reset } from './winkelvoorraaditem.reducer';

export const WinkelvoorraaditemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const winkelvoorraaditemEntity = useAppSelector(state => state.winkelvoorraaditem.entity);
  const loading = useAppSelector(state => state.winkelvoorraaditem.loading);
  const updating = useAppSelector(state => state.winkelvoorraaditem.updating);
  const updateSuccess = useAppSelector(state => state.winkelvoorraaditem.updateSuccess);

  const handleClose = () => {
    navigate('/winkelvoorraaditem');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProducts({}));
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
      ...winkelvoorraaditemEntity,
      ...values,
      betreftProduct: products.find(it => it.id.toString() === values.betreftProduct?.toString()),
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
          ...winkelvoorraaditemEntity,
          betreftProduct: winkelvoorraaditemEntity?.betreftProduct?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.winkelvoorraaditem.home.createOrEditLabel" data-cy="WinkelvoorraaditemCreateUpdateHeading">
            Create or edit a Winkelvoorraaditem
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
                <ValidatedField name="id" required readOnly id="winkelvoorraaditem-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Aantal" id="winkelvoorraaditem-aantal" name="aantal" data-cy="aantal" type="text" />
              <ValidatedField
                label="Aantalinbestelling"
                id="winkelvoorraaditem-aantalinbestelling"
                name="aantalinbestelling"
                data-cy="aantalinbestelling"
                type="text"
              />
              <ValidatedField
                label="Datumleveringbestelling"
                id="winkelvoorraaditem-datumleveringbestelling"
                name="datumleveringbestelling"
                data-cy="datumleveringbestelling"
                type="date"
              />
              <ValidatedField label="Locatie" id="winkelvoorraaditem-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField
                id="winkelvoorraaditem-betreftProduct"
                name="betreftProduct"
                data-cy="betreftProduct"
                label="Betreft Product"
                type="select"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/winkelvoorraaditem" replace color="info">
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

export default WinkelvoorraaditemUpdate;
