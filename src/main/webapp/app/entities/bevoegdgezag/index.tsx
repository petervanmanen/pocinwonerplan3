import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bevoegdgezag from './bevoegdgezag';
import BevoegdgezagDetail from './bevoegdgezag-detail';
import BevoegdgezagUpdate from './bevoegdgezag-update';
import BevoegdgezagDeleteDialog from './bevoegdgezag-delete-dialog';

const BevoegdgezagRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bevoegdgezag />} />
    <Route path="new" element={<BevoegdgezagUpdate />} />
    <Route path=":id">
      <Route index element={<BevoegdgezagDetail />} />
      <Route path="edit" element={<BevoegdgezagUpdate />} />
      <Route path="delete" element={<BevoegdgezagDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BevoegdgezagRoutes;
