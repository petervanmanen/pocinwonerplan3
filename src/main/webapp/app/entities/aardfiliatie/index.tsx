import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aardfiliatie from './aardfiliatie';
import AardfiliatieDetail from './aardfiliatie-detail';
import AardfiliatieUpdate from './aardfiliatie-update';
import AardfiliatieDeleteDialog from './aardfiliatie-delete-dialog';

const AardfiliatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aardfiliatie />} />
    <Route path="new" element={<AardfiliatieUpdate />} />
    <Route path=":id">
      <Route index element={<AardfiliatieDetail />} />
      <Route path="edit" element={<AardfiliatieUpdate />} />
      <Route path="delete" element={<AardfiliatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AardfiliatieRoutes;
